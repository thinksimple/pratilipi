package com.pratilipi.api.impl.userpratilipi.shared;

import javax.jdo.annotations.Persistent;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.UserReviewState;

@SuppressWarnings("unused")
public class PostUserPratilipiRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;
	
	private Integer rating;
	private boolean hasRating;
	
	private String reviewTitle;
	private boolean hasReviewTitle;

	private String review;
	private boolean hasReview;

	private UserReviewState reviewState;
	private boolean hasReviewState;

	
	public Long getPratilipiId() {
		return pratilipiId;
	}
	
}
