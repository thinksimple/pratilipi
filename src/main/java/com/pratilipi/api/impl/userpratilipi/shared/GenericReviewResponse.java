package com.pratilipi.api.impl.userpratilipi.shared;

import java.util.Date;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.data.client.UserPratilipiData;

@SuppressWarnings("unused")
public class GenericReviewResponse extends GenericResponse {
	
	private String userPratilipiId;
	private String userName;
	private String userImageUrl;
	private String userProfilePageUrl;

	private Integer rating;
	private String review;
	private Long reviewDateMillis;
	
	private Long commentCount;

	
	private GenericReviewResponse() { }
	
	public GenericReviewResponse( UserPratilipiData userPratilipiData ) {
		
		this.userPratilipiId = userPratilipiData.getId();
		this.userName = userPratilipiData.getUserName();
		this.userImageUrl = userPratilipiData.getUserImageUrl();
		this.userProfilePageUrl = userPratilipiData.getUserProfilePageUrl();
		this.rating = userPratilipiData.getRating();
		this.review = userPratilipiData.getReview();
		this.reviewDateMillis = userPratilipiData.getReviewDate() == null ? null : userPratilipiData.getReviewDate().getTime();
		this.commentCount = userPratilipiData.getCommentCount();
		
	}
	
	
	public String getId() {
		return userPratilipiId;
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
		return reviewDateMillis == null ? null : reviewDateMillis;
	}
	
	public Long getCommentCount() {
		return commentCount;
	}
	
}
