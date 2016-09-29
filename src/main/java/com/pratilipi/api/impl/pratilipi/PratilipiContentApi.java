package com.pratilipi.api.impl.pratilipi;

import java.util.Map;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.data.util.PratilipiDocUtil;
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings( "serial" )
@Bind( uri = "/pratilipi/content" )
public class PratilipiContentApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		@Validate( required = true, minInt = 1, requiredErrMsg = ERR_PRATILIPI_CHAPTER_NO_REQUIRED )
		private Integer chapterNo;

		private Integer pageNo;

		private PratilipiContentType contentType;


		public void setPratilipiId( Long pratilipiId ) {
			this.pratilipiId = pratilipiId;
		}

		public void setChapterNo( Integer chapterNo ) {
			this.chapterNo = chapterNo;
		}

		public void setPageNo( Integer pageNo ) {
			this.pageNo = pageNo;
		}

		public void setContentType( PratilipiContentType contentType ) {
			this.contentType = contentType;
		}

	}

	public static class PostRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		@Validate( required = true, minInt = 1, requiredErrMsg = ERR_PRATILIPI_CHAPTER_NO_REQUIRED )
		private Integer chapterNo;

		@Validate( required = true, minInt = 1, requiredErrMsg = ERR_PRATILIPI_PAGE_NO_REQUIRED )
		private Integer pageNo;
		
		@Validate( required = true )
		private String content;

	}

	@SuppressWarnings("unused")
	public static class Response extends GenericResponse {

		private Long pratilipiId;
		private Integer chapterNo;
		private Integer pageNo;
		private String chapterTitle;
		private Object content;


		private Response() {}

		public Response( Long pratilipiId, Integer chapterNo, Integer pageNo, String chapterTitle, Object content ) {
			this.pratilipiId = pratilipiId;
			this.chapterNo = chapterNo;
			this.pageNo = pageNo;
			this.chapterTitle = chapterTitle;
			this.content = content;
		}

		public Object getContent() {
			return content;
		}

	}


	@Get
	public GenericResponse getPratilipiContent( GetRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiContentType contentType = request.contentType;

		if( request.contentType == null )
			contentType = DataAccessorFactory.getDataAccessor()
					.getPratilipi( request.pratilipiId )
					.getContentType();


		if( PratilipiDataUtil.isOldFormatContent( request.pratilipiId ) ) {

			Object content = PratilipiDataUtil.getPratilipiContent(
					request.pratilipiId,
					request.chapterNo,
					request.pageNo,
					contentType );

			if( UxModeFilter.isAndroidApp() ) {
				return new Response(
						request.pratilipiId,
						request.chapterNo,
						request.pageNo,
						null,
						content );

			} else if( contentType == PratilipiContentType.PRATILIPI ) {
				return new Response(
						request.pratilipiId,
						request.chapterNo,
						request.pageNo,
						null,
						content );

			} else if( contentType == PratilipiContentType.IMAGE ) {
				BlobEntry blobEntry = ( BlobEntry ) content;
				return new GenericFileDownloadResponse(
						blobEntry.getData(),
						blobEntry.getMimeType(),
						blobEntry.getETag() );

			}

		} else {

			Map<String, Object> contentMap = PratilipiDocUtil.getContent( request.pratilipiId, request.chapterNo, 1, ! UxModeFilter.isAndroidApp() );

			return new Response( request.pratilipiId, 
					request.chapterNo, 
					request.pageNo,
					contentMap != null && contentMap.get( "chapterTitle" ) != null ? contentMap.get( "chapterTitle" ).toString() : null, 
					contentMap != null && contentMap.get( "content" ) != null ? contentMap.get( "content" ) : null );

		}

		return new GenericResponse();

	}

	@Post
	public Response postPratilipiContent( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		@SuppressWarnings("unused")
		int pageCount = PratilipiDataUtil.updatePratilipiContent(
				request.pratilipiId,
				request.pageNo,
				PratilipiContentType.PRATILIPI,
				request.content,
				false );

		Task task = TaskQueueFactory.newTask()
				.setUrl( "/pratilipi/process" )
				.addParam( "pratilipiId", request.pratilipiId.toString() )
				.addParam( "processContent", "true" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );

		return new Response();

	}		

}
