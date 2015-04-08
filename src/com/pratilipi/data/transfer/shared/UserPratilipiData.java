package com.pratilipi.data.transfer.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.BookmarkRequestType;
import com.pratilipi.commons.shared.UserReviewState;

public class UserPratilipiData implements IsSerializable {

	private Long userId;
	
	private String userName;
	
	private Long pratilipiId;
	
	
	private Integer rating;
	private boolean hasRating;
	
	private String review;
	private boolean hasReview;
	
	private UserReviewState reviewState;
	
	private Date reviewDate;
	
	private String bookmarks;
	private boolean hasBookmarks;
	private BookmarkRequestType bookmarkRequestType;
	
	
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
	
	public Boolean hasRating() {
		return hasRating;
	}

	public String getReview() {
		return review;
	}

	public void setReview( String review ) {
		this.review = review;
		this.hasReview = true;
	}
	
	public Boolean hasReview() {
		return hasReview;
	}

	public UserReviewState getReviewState() {
		return reviewState;
	}

	public void setReviewState( UserReviewState reviewState ) {
		this.reviewState = reviewState;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate( Date reviewDate ) {
		this.reviewDate = reviewDate;
	}
	
	public String getBookmarks() {
		return bookmarks;
	}
	
	public void setBookmarks( String bookmarks ) {
		this.bookmarks = bookmarks;
		this.hasBookmarks = true;
	}
	
	public Boolean hasBookmarks() {
		return hasBookmarks;
	}
	
	public BookmarkRequestType getBookmarkRequestType() {
		return bookmarkRequestType;
	}
	
	public void setBookmarkRequestType( BookmarkRequestType bookmarkRequestType ) {
		this.bookmarkRequestType = bookmarkRequestType;
	}
	
}
