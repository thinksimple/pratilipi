package com.pratilipi.api.impl.userpratilipi.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserPratilipiData;

@SuppressWarnings("unused")
public class GetUserPratilipiReviewListResponse extends GenericResponse { 
	
	private List<UserPratilipiData> reviewList;
	private String cursor;

	
	private GetUserPratilipiReviewListResponse() {}
	
	public GetUserPratilipiReviewListResponse( List<UserPratilipiData> reviewList, String cursor ) {
		this.reviewList = reviewList;
		this.cursor = cursor;
	}
	
}
