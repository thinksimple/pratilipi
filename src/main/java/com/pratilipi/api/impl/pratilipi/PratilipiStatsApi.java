package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/stats" )
public class PratilipiStatsApi extends GenericApi {
	
	public static class Request extends GenericRequest {
		
		@Validate( required = true, minLong = 1L )
		private Long pratilipiId;
		
		private Long readCountOffset;
		private Long readCount;

		private Long fbLikeShareCountOffset;
		private Long fbLikeShareCount;
		
	}
	
	
	@Post
	public GenericResponse post( Request request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		Pratilipi pratilipi = DataAccessorFactory.getDataAccessor().getPratilipi( request.pratilipiId );
		if( pratilipi == null || pratilipi.getState() != PratilipiState.PUBLISHED )
			return new GenericResponse();
		
		
		PratilipiDataUtil.updatePratilipiStats(
				request.pratilipiId,
				request.readCountOffset,
				request.readCount,
				request.fbLikeShareCountOffset,
				request.fbLikeShareCount );

		PratilipiDataUtil.updatePratilipiSearchIndex( request.pratilipiId, null );
		
		if( pratilipi.getAuthorId() != null && ( request.readCountOffset != null || request.readCount != null ) ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", pratilipi.getAuthorId().toString() )
					.addParam( "updateStats", "true" );
			TaskQueueFactory.getAuthorOfflineTaskQueue().add( task );
		}
		
		return new GenericResponse();
	
	}
	
}
