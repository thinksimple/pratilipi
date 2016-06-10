package com.pratilipi.api.impl.userpratilipi.shared;

import java.util.Date;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.data.client.UserPratilipiData;

public class GenericUserPratilipiResponse extends GenericResponse {

	private String userPratilipiId;
	private String userName;
	private String userImageUrl;
	private String userProfilePageUrl;
	private Long pratilipiId;
	
	private Integer rating;
	private String reviewTitle;
	private String review;
	private UserReviewState reviewState;
	private Long reviewDateMillis;
	private Boolean addedToLib;
	
	private Boolean hasAccessToReview;
	
	private Long commentCount;
	
	
	@SuppressWarnings("unused")
	private GenericUserPratilipiResponse() {}
	
	public GenericUserPratilipiResponse( UserPratilipiData userPratilipiData ) {

		userPratilipiId = userPratilipiData.getId();
		userName = userPratilipiData.getUserName();
		userImageUrl = userPratilipiData.getUserImageUrl();
		userProfilePageUrl = userPratilipiData.getUserProfilePageUrl();
		
		pratilipiId = userPratilipiData.getPratilipiId();
		
		rating = userPratilipiData.getRating();
		reviewTitle = userPratilipiData.getReviewTitle();
		review = userPratilipiData.getReview();
		reviewState = userPratilipiData.getReviewState();
		if( userPratilipiData.getReviewDate() != null )
			reviewDateMillis = userPratilipiData.getReviewDate().getTime();
		addedToLib = userPratilipiData.isAddedToLib();
		
		hasAccessToReview = userPratilipiData.hasAccessToReview();
		
		commentCount = userPratilipiData.getCommentCount();
		
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
	
	public Boolean isAddedtoLib() {
		return addedToLib;
	}

	public void setAddedtoLib( Boolean addedToLib ) {
		this.addedToLib = addedToLib;
	}

	
	public Boolean getHasAccessToReview() {
		return hasAccessToReview;
	}

	
	public Long getCommentCount() {
		return commentCount;
	}

}
