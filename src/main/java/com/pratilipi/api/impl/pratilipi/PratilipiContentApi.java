package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiContentRequest;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiContentResponse;
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
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content" )
public class PratilipiContentApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Long pratilipiId;

		@Validate( required = true )
		private Integer chapterNo;
		
		@Validate
		private Integer pageNo;
		
		@Validate
		private String pageletId;
		
		private PratilipiContentType contentType;

	}
	
	public static class Response extends GenericResponse {
		
		private Long pratilipiId;
		private Integer pageNo;
		private Object pageContent;

		
		private Response() {}
		
		private Response( Long pratilipiId, Integer pageNo, Object pageContent ) {
			this.pratilipiId = pratilipiId;
			this.pageNo = pageNo;
			this.pageContent = pageContent;
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

		Object content = PratilipiDataUtil.getPratilipiContent(
				request.pratilipiId,
				request.chapterNo,
				request.pageNo,
				contentType );

		if( contentType == PratilipiContentType.PRATILIPI ) {
			return new Response(
					request.pratilipiId,
					request.pageNo,
					content );
		} else if( contentType == PratilipiContentType.IMAGE ) {
			BlobEntry blobEntry = ( BlobEntry ) content;
			return new GenericFileDownloadResponse( blobEntry.getData(), 
					blobEntry.getMimeType(), 
					blobEntry.getETag() );
		}
		
		return new GenericResponse();
		
	}

	@Post
	public PostPratilipiContentResponse postPratilipiContent( PostPratilipiContentRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		int pageCount = PratilipiDataUtil.updatePratilipiContent(
				request.getPratilipiId(),
				request.getPageNumber(),
				PratilipiContentType.PRATILIPI,
				request.getPageContent(),
				request.getInsertNew() );
		
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/pratilipi/process" )
				.addParam( "pratilipiId", request.getPratilipiId().toString() )
				.addParam( "processContent", "true" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );
			
		return new PostPratilipiContentResponse( request.getPageNumber(), pageCount );
		
	}		

}
