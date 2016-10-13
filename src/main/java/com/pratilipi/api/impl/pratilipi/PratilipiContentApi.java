package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiContentDoc;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.data.util.PratilipiDocUtil;
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings( "serial" )
@Bind( uri = "/pratilipi/content" )
public class PratilipiContentApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED, minLong = 1L )
		private Long pratilipiId;

		@Validate( minInt = 1 )
		private Integer chapterNo;

		@Validate( minInt = 1 )
		private Integer pageNo;


 		public void setPratilipiId( Long pratilipiId ) {
			this.pratilipiId = pratilipiId;
		}

		public void setChapterNo( Integer chapterNo ) {
			this.chapterNo = chapterNo;
		}

		public void setPageNo( Integer pageNo ) {
			this.pageNo = pageNo;
		}

	}

	public static class PostRequest extends GenericRequest {

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED, minLong = 1L )
		private Long pratilipiId;

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_CHAPTER_NO_REQUIRED, minInt = 1 )
		private Integer chapterNo;

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_PAGE_NO_REQUIRED, minInt = 1 )
		private Integer pageNo;

		private String chapterTitle;

		private String content;

	}

	@SuppressWarnings("unused")
	public static class GetResponse extends GenericResponse {

		private Long pratilipiId;
		private Integer chapterNo;
		private String chapterTitle;
		private Integer pageNo;
		private Object content;


		private GetResponse() {}

		public GetResponse( Long pratilipiId, Integer chapterNo, String chapterTitle, Integer pageNo, Object content ) {
			this.pratilipiId = pratilipiId;
			this.chapterNo = chapterNo;
			this.chapterTitle = chapterTitle;
			this.pageNo = pageNo;
			this.content = content;
		}

		
		public String getChapterTitle() {
			return chapterTitle;
		}

		public Object getContent() {
			return content;
		}

	}


	@Get
	public GetResponse get( GetRequest request )
			throws InvalidArgumentException, InsufficientAccessException,
			UnexpectedServerException {

		if( UxModeFilter.isAndroidApp() ) {
			
			Object content = PratilipiDocUtil.getContent(
					request.pratilipiId,
					request.chapterNo,
					request.pageNo );
			
			return new GetResponse(
					request.pratilipiId,
					request.chapterNo,
					null,
					request.pageNo,
					content );
		
		} else {
		
			if( request.chapterNo == null )
				request.chapterNo = 1;
		
			Pratilipi pratilipi = DataAccessorFactory.getDataAccessor().getPratilipi( request.pratilipiId );
		
			if( pratilipi.isOldContent() ) {
				
				String content = PratilipiDataUtil.getPratilipiContent(
						request.pratilipiId,
						request.chapterNo );
	
				return new GetResponse(
						request.pratilipiId,
						request.chapterNo,
						null,
						request.pageNo,
						content );

			} else { // New Content

				Object content = PratilipiDocUtil.getContent(
						request.pratilipiId,
						request.chapterNo,
						request.pageNo );

				
				if( content == null ) {
					
					return new GetResponse(
							request.pratilipiId,
							request.chapterNo,
							null,
							request.pageNo,
							"" );
					
				} else if( request.pageNo != null ) {
					
					PratilipiContentDoc.Page page = (PratilipiContentDoc.Page) content;
					return new GetResponse(
							request.pratilipiId,
							request.chapterNo,
							null,
							request.pageNo,
							page.getHtml() );

				} else {
					
					PratilipiContentDoc.Chapter chapter = (PratilipiContentDoc.Chapter) content;
					
					String contentHtml = "";
					for( PratilipiContentDoc.Page page : chapter.getPageList() )
						contentHtml += page.getHtml();
					
					return new GetResponse(
							request.pratilipiId,
							request.chapterNo,
							chapter.getTitle(),
							request.pageNo,
							contentHtml );
					
				}
				
			}

		}

	}

	@Post
	public GenericResponse post( PostRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		PratilipiDocUtil.savePageContent(
				request.pratilipiId, 
				request.chapterNo, 
				request.chapterTitle, 
				request.pageNo, 
				request.content );

		Task task = TaskQueueFactory.newTask()
				.setUrl( "/pratilipi/process" )
				.addParam( "pratilipiId", request.pratilipiId.toString() )
				.addParam( "processContent", "true" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );

		return new GenericResponse();

	}

}
