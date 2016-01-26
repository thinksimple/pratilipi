package com.pratilipi.api.impl.userpratilipi.shared;

import java.util.Date;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.UserReviewState;

public class GenericUserPratilipiResponse extends GenericResponse {

	private String userPratilipiId;
	private String userName;
	private String userImageUrl;
	private Long pratilipiId;
	
	private Integer rating;
	private String reviewTitle;
	private String review;
	private UserReviewState reviewState;
	private Long reviewDateMills;
	private Boolean hasAccessToReview;
	
	
	public String getId() {
		return userPratilipiId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getUserImageUrl() {
		return userImageUrl;
	}
	
	public Long getPratilipiId() {
		return pratilipiId;
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
	
	public UserReviewState getReviewState() {
		return reviewState;
	}
	
	public Date getReviewDateMills() {
		return reviewDateMills == null ? null : new Date( reviewDateMills );
	}
	
	
	public Boolean getHasAccessToReview() {
		return hasAccessToReview;
	}
	
}
