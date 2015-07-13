package com.pratilipi.api.userpratilipi.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserPratilipiData;

@SuppressWarnings("unused")
public class GetPratilipiReviewResponse extends GenericResponse { 
	
	private List<UserPratilipiData> reviewList;
	private String cursor;

	
	private GetPratilipiReviewResponse() {}
	
	public GetPratilipiReviewResponse( List<UserPratilipiData> reviewList, String cursor ) {
		this.reviewList = reviewList;
		this.cursor = cursor;
	}
	
}
