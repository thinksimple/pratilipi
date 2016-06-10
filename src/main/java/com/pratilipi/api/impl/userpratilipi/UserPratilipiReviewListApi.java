package com.pratilipi.api.impl.userpratilipi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/review/list" )
public class UserPratilipiReviewListApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Long pratilipiId;
		
		private String cursor;
		private Integer resultCount;

	}
	
	@SuppressWarnings("unused")
	public static class Response extends GenericResponse {
		
		private List<Review> reviewList;
		private String cursor;

		
		Response() {}
		
		Response( List<UserPratilipiData> reviewList, String cursor ) {
			this.reviewList = new ArrayList<>( reviewList.size() );
			for( UserPratilipiData review : reviewList )
				this.reviewList.add( new Review( review ) );
			this.cursor = cursor;
		}
		
	}

	public static class Review extends GenericResponse {
		
		private String userPratilipiId;
		
		@Deprecated
		private String userName;
		@Deprecated
		private String userImageUrl;
		@Deprecated
		private String userProfilePageUrl;

		private UserPratilipiApi.User user;
		
		private Integer rating;
		private String review;
		private Long reviewDateMillis;
		
		private Long commentCount;

		
		@SuppressWarnings("unused")
		private Review() { }
		
		// TODO: Change it to package only ASAP
		public Review( UserPratilipiData userPratilipiData ) {
			
			this.userPratilipiId = userPratilipiData.getId();
			
			this.userName = userPratilipiData.getUserName();
			this.userImageUrl = userPratilipiData.getUserImageUrl();
			this.userProfilePageUrl = userPratilipiData.getUserProfilePageUrl();

			this.user = new UserPratilipiApi.User( userPratilipiData.getUser() );
			
			this.rating = userPratilipiData.getRating();
			this.review = userPratilipiData.getReview();
			this.reviewDateMillis = userPratilipiData.getReviewDate() == null ? null : userPratilipiData.getReviewDate().getTime();
			
			this.commentCount = userPratilipiData.getCommentCount();
			
		}
		
		
		public String getId() {
			return userPratilipiId;
		}

		
		@Deprecated
		public String getUserName() {
			return userName;
		}
		
		@Deprecated
		public String getUserImageUrl() {
			return userImageUrl;
		}
		
		@Deprecated
		public String getUserImageUrl( int width ) {
			return userImageUrl.indexOf( '?' ) == -1
					? userImageUrl + "?width=" + width
					: userImageUrl + "&width=" + width;
		}

		@Deprecated
		public String getUserProfilePageUrl() {
			return userProfilePageUrl;
		}

		
		public UserPratilipiApi.User getUser() {
			return user;
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
		
		public Long getCommentCount() {
			return commentCount;
		}
		
	}

	
	@Get
	public Response get( GetRequest request ) throws UnexpectedServerException {
		
		DataListCursorTuple<UserPratilipiData> userPratilipiListCursorTuple =
				UserPratilipiDataUtil.getPratilipiReviewList(
						request.pratilipiId,
						request.cursor,
						null,
						request.resultCount == null ? 20 : request.resultCount );
		
		return new Response(
				userPratilipiListCursorTuple.getDataList(),
				userPratilipiListCursorTuple.getCursor() );
	
	}

}
