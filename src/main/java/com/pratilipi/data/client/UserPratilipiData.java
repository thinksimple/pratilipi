package com.pratilipi.data.client;

import java.util.Date;

public class UserPratilipiData {

	private Long userId;
	
	private String userName;
	
	private Long pratilipiId;
	
	
	private Integer rating;
	private boolean hasRating;
	
	private String review;
	private boolean hasReview;
	
	private Long reviewDate;
	
	
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

	public Date getReviewDate() {
		return reviewDate == null ? null : new Date( reviewDate );
	}

	public void setReviewDate( Date reviewDate ) {
		this.reviewDate = reviewDate == null ? null : reviewDate.getTime();
	}
	
}
