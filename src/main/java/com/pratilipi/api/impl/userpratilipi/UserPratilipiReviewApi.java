package com.pratilipi.api.impl.userpratilipi;

import java.util.Date;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
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
	
	public class Response extends GenericResponse {
		
		private Long userId;
		private String userName;
		private String userImageUrl;
		private String userProfilePageUrl;

		private Integer rating;
		private String review;
		private Long reviewDateMillis;

		
		public Response( UserPratilipiData userPratilipiData ) {
			this.userId = userPratilipiData.getUserId();
			this.userName = userPratilipiData.getUserName();
			this.userImageUrl = userPratilipiData.getUserImageUrl();
			this.userProfilePageUrl = userPratilipiData.getUserProfilePageUrl();
			this.rating = userPratilipiData.getRating();
			this.review = userPratilipiData.getReview();
			this.reviewDateMillis = userPratilipiData.getReviewDate().getTime();
		}
		
		
		public Long getUserId() {
			return userId;
		}
		
		public String getUserName() {
			return userName;
		}
		
		public String getUserImageUrl() {
			return userImageUrl;
		}
		
		public String getUserProfilePageUrl() {
			return userProfilePageUrl;
		}

		public Integer getRating() {
			return rating;
		}
		
		public String getReview() {
			return review;
		}
		
		public Date getReviewDate() {
			return reviewDateMillis == null ? null : new Date( reviewDateMillis );
		}
		
		public Long getReviewDateMillis() {
			return reviewDateMillis;
		}
		
	}
	
	
	@Post
	public Response postUserPratilipi( PostRequest request )
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
				.addParam( "updatePratilipiReviewsDoc", "true" )
				.addParam( "updateUserPratilipiStats", "true" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );
		
		return new Response( userPratilipiData );
		
	}		

}
