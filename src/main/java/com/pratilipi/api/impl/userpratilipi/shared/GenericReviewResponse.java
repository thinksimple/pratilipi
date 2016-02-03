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

	private Integer rating;
	private String reviewTitle;
	private String review;
	private Long reviewDateMillis;

	
	private GenericReviewResponse() { }
	
	public GenericReviewResponse( UserPratilipiData userPratilipiData ) {
		
		this.userPratilipiId = userPratilipiData.getId();
		this.userName = userPratilipiData.getUserName();
		this.userImageUrl = userPratilipiData.getUserImageUrl();
		this.rating = userPratilipiData.getRating();
		this.reviewTitle = userPratilipiData.getReviewTitle();
		this.review = userPratilipiData.getReview();
		this.reviewDateMillis = userPratilipiData.getReviewDate() == null ? null : userPratilipiData.getReviewDate().getTime();
		
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
	
	public Integer getRating() {
		return rating;
	}
	
	public String getReviewTitle() {
		return reviewTitle;
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
	
}
