package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiContentRequest;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiContentResponse;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiContentRequest;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiContentResponse;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
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

	@Get
	public GenericResponse getPratilipiContent( GetPratilipiContentRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		PratilipiContentType contentType = request.getContentType();
		
		if( contentType == null )
			contentType = DataAccessorFactory.getDataAccessor()
							.getPratilipi( request.getPratilipiId() ).getContentType();

		Object content = PratilipiDataUtil.getPratilipiContent(
				request.getPratilipiId(),
				request.getChapterNo(),
				request.getPageNo(),
				contentType );

		if( contentType == PratilipiContentType.PRATILIPI ) {
			return new GetPratilipiContentResponse(
					request.getPratilipiId(),
					request.getPageNo(),
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
