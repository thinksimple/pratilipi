package com.pratilipi.pagecontent.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.taskqueue.Task;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiContentRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiContentResponse;
import com.pratilipi.pagecontent.pratilipi.api.shared.PutPratilipiContentRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.PutPratilipiContentResponse;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content" )
public class PratilipiContentApi extends GenericApi {

	@Get
	public GetPratilipiContentResponse getPratilipiContent( GetPratilipiContentRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		String content = (String) PratilipiContentHelper.getPratilipiContent(
				request.getPratilipiId(),
				request.getPageNumber(),
				PratilipiContentType.PRATILIPI,
				this.getThreadLocalRequest() );
		
		return new GetPratilipiContentResponse(
				request.getPratilipiId(),
				request.getPageNumber(), 
				content );
	}

	@Put
	public PutPratilipiContentResponse putPratilipiContent( PutPratilipiContentRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		int pageCount = PratilipiContentHelper.updatePratilipiContent(
				request.getPratilipiId(),
				request.getPageNumber(),
				PratilipiContentType.PRATILIPI,
				request.getPageContent(),
				request.getInsertNew(),
				this.getThreadLocalRequest() );
		
		Task task = TaskQueueFactory.newTask()
			.addParam( "pratilipiId", request.getPratilipiId().toString() )
			.addParam( "processContent", "true" )
			.setUrl( "/api.pratilipi/pratilipi/process" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );
			
		return new PutPratilipiContentResponse( request.getPageNumber(), pageCount );
	}		

}
