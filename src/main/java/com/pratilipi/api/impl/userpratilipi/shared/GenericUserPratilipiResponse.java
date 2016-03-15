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
	private Long reviewDateMillis;
	private Boolean hasAccessToReview;
	private Boolean isAddedtoLib;
	
	
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
	
	public Date getReviewDate() {
		return reviewDateMillis == null ? null : new Date( reviewDateMillis );
	}
	
	public Long getReviewDateMillis() {
		return reviewDateMillis == null ? null : reviewDateMillis;
	}
	
	
	public Boolean getHasAccessToReview() {
		return hasAccessToReview;
	}

	public Boolean isAddedtoLib() {
		return isAddedtoLib;
	}

	public void setIsAddedtoLib(Boolean isAddedtoLib) {
		this.isAddedtoLib = isAddedtoLib;
	}
	
}
