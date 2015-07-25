package com.pratilipi.api.userpratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

@SuppressWarnings("unused")
public class PutUserPratilipiRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;
	
	private Integer rating;
	private boolean hasRating;
	
	private String reviewTitle;
	private boolean hasReviewTitle;

	private String review;
	private boolean hasReview;

}
