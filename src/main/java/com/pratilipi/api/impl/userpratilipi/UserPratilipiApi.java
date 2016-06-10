package com.pratilipi.api.impl.userpratilipi;

import java.util.Date;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi" )
public class UserPratilipiApi extends GenericApi {
	
	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Long pratilipiId;

	}
	
	public static class Response extends GenericResponse {

		private String userPratilipiId;
		
		private Long userId;
		private Long pratilipiId;
		
		@Deprecated
		private String userName;
		@Deprecated
		private String userImageUrl;
		@Deprecated
		private String userProfilePageUrl;
		
		private User user;
		
		private Integer rating;
		private String review;
		private UserReviewState reviewState;
		private Long reviewDateMillis;
		
		private Long commentCount;
		
		private Boolean addedToLib;
		
		private Boolean hasAccessToReview;
		
		
		@SuppressWarnings("unused")
		private Response() {}
		
		// TODO: Change access to package level ASAP
		public Response( UserPratilipiData userPratilipiData ) {

			userPratilipiId = userPratilipiData.getId();
			userId = userPratilipiData.getUserId();
			pratilipiId = userPratilipiData.getPratilipiId();
			
			userName = userPratilipiData.getUserName();
			userImageUrl = userPratilipiData.getUserImageUrl();
			userProfilePageUrl = userPratilipiData.getUserProfilePageUrl();

			user = new User( userPratilipiData.getUser() );
			
			rating = userPratilipiData.getRating();
			review = userPratilipiData.getReview();
			reviewState = userPratilipiData.getReviewState();
			if( userPratilipiData.getReviewDate() != null )
				reviewDateMillis = userPratilipiData.getReviewDate().getTime();

			commentCount = userPratilipiData.getCommentCount();
			
			addedToLib = userPratilipiData.isAddedToLib();
			
			hasAccessToReview = userPratilipiData.hasAccessToReview();
			
		}
		
		
		public String getId() {
			return userPratilipiId;
		}
		
		public Long getUserId() {
			return userId;
		}
		
		public Long getPratilipiId() {
			return pratilipiId;
		}

		
		public String getUserName() {
			return userName;
		}
		
		public String getUserImageUrl() {
			return userImageUrl;
		}

		public String getUserImageUrl( int width ) {
			return userImageUrl.indexOf( '?' ) == -1
					? userImageUrl + "?width=" + width
					: userImageUrl + "&width=" + width;
		}

		public String getUserProfilePageUrl() {
			return userProfilePageUrl;
		}
		

		public User getUser() {
			return user;
		}
		

		public Integer getRating() {
			return rating;
		}
		
		public String getReview() {
			return review;
		}
		
		public UserReviewState getReviewState() {
			return reviewState;
		}
		
		public Date getReviewDate() {
			return reviewDateMillis == null ? null : new Date( reviewDateMillis );
		}
		
		public Long getReviewDateMillis() {
			return reviewDateMillis == null ? null : reviewDateMillis;
		}
		
		
		public Long getCommentCount() {
			return commentCount;
		}

		
		public Boolean isAddedtoLib() {
			return addedToLib;
		}
		

		public Boolean getHasAccessToReview() {
			return hasAccessToReview;
		}

	}
	
	public static class User {
		
		private Long userId;
		private String displayName;
		private String profilePageUrl;
		private String profileImageUrl;
	
		
		@SuppressWarnings("unused")
		private User() {}
		
		User( UserData userData ) {
			userId = userData.getId();
			displayName = userData.getDisplayName();
			profilePageUrl = userData.getProfilePageUrl();
			profileImageUrl = userData.getProfileImageUrl();
		}
		
		
		public Long getId() {
			return userId;
		}

		public String getDisplayName() {
			return displayName;
		}

		public String getProfilePageUrl() {
			return profilePageUrl;
		}
		
		public String getProfileImageUrl() {
			return profileImageUrl;
		}
		
		public String getUserImageUrl( int width ) {
			return profileImageUrl.indexOf( '?' ) == -1
					? profileImageUrl + "?width=" + width
					: profileImageUrl + "&width=" + width;
		}
		
	}
	
	
	@Get
	public Response getUserPratilipi( GetRequest request ) throws UnexpectedServerException {

		UserPratilipiData userPratilipiData = UserPratilipiDataUtil.getUserPratilipi(
				AccessTokenFilter.getAccessToken().getUserId(),
				request.pratilipiId );

		return new Response( userPratilipiData );
		
	}		

}
