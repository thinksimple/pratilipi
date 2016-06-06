package com.pratilipi.api.impl.userpratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.impl.userpratilipi.shared.GenericUserPratilipiResponse;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/review" )
public class UserPratilipiReviewApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {
		
		@Validate( required = true, minLong = 1L )
		private Long pratilipiId;
		
		private Integer rating;
		private boolean hasRating;
		
		private String review;
		private boolean hasReview;
		
		private UserReviewState reviewState;
		private boolean hasReviewState;
		
	}
	
	
	@Post
	public GenericUserPratilipiResponse post( PostRequest request )
			throws InsufficientAccessException {

		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setUserId( AccessTokenFilter.getAccessToken().getUserId() );
		userPratilipiData.setPratilipiId( request.pratilipiId );

		if( request.hasRating )
			userPratilipiData.setRating( request.rating );
		if( request.hasReview )
			userPratilipiData.setReview( request.review );
		if( request.hasReviewState )
			userPratilipiData.setReviewState( request.reviewState );
		
		userPratilipiData = UserPratilipiDataUtil.saveUserPratilipi( userPratilipiData );
		
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/pratilipi/process" )
				.addParam( "pratilipiId", request.pratilipiId.toString() )
				.addParam( "updateReviewsDoc", "true" )
				.addParam( "updateUserPratilipiStats", "true" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );
		
		return new GenericUserPratilipiResponse( userPratilipiData );
		
	}		

}
