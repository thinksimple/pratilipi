package com.pratilipi.api.userpratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PutPratilipiReviewRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;
	
	private Integer rating;
	private boolean hasRating;
	
	private String review;
	private boolean hasReview;

	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Integer getRating() {
		return rating;
	}

	public boolean hasRating() {
		return hasRating;
	}

	public String getReview() {
		return review;
	}

	public boolean hasReview() {
		return hasReview;
	}

}
