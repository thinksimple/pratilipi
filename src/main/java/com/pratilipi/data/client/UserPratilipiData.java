package com.pratilipi.data.client;

import java.util.Date;

import com.pratilipi.common.type.UserReviewState;

public class UserPratilipiData {

	private String userPratilipiId;
	
	private Long userId;
	
	private String userName;
	
	private Long pratilipiId;
	
	
	private Integer rating;
	private boolean hasRating;
	
	private String reviewTitle;
	private boolean hasReviewTitle;
	
	private String review;
	private boolean hasReview;
	
	private UserReviewState reviewState;
	private boolean hasReviewState;
	
	private Long reviewDateMillis;
	

	
	public String getId() {
		return userPratilipiId;
	}
	
	public void setId( String id ) {
		this.userPratilipiId = id;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId( Long userId ) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName( String userName ) {
		this.userName = userName;
	}

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}

	
	public Integer getRating() {
		return rating;
	}

	public void setRating( Integer rating ) {
		this.rating = rating;
		this.hasRating = true;
	}
	
	public boolean hasRating() {
		return hasRating;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle( String reviewTitle ) {
		this.reviewTitle = reviewTitle;
		this.hasReviewTitle = true;
	}
	
	public boolean hasReviewTitle() {
		return hasReviewTitle;
	}

	public String getReview() {
		return review;
	}

	public void setReview( String review ) {
		this.review = review;
		this.hasReview = true;
	}
	
	public boolean hasReview() {
		return hasReview;
	}

	public UserReviewState getReviewState() {
		return reviewState;
	}

	public void setReviewState( UserReviewState reviewState ) {
		this.reviewState = reviewState;
		this.hasReviewState = true;
	}
	
	public boolean hasReviewState() {
		return hasReviewState;
	}

	public Date getReviewDate() {
		return reviewDateMillis == null ? null : new Date( reviewDateMillis );
	}

	public void setReviewDate( Date reviewDate ) {
		this.reviewDateMillis = reviewDate == null ? null : reviewDate.getTime();
	}
	
}
