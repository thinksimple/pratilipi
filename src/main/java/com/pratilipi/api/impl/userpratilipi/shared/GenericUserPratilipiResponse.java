package com.pratilipi.api.impl.userpratilipi.shared;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.UserReviewState;

@SuppressWarnings("unused")
public class GenericUserPratilipiResponse extends GenericResponse {

	private String userPratilipiId;
	private String userName;
	private Long pratilipiId;
	
	private Integer rating;
	private String reviewTitle;
	private String review;
	private UserReviewState reviewState;
	private Long reviewDateMills;
	private Boolean hasAccessToReview;
	
}
