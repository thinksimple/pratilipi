package com.pratilipi.api.userpratilipi.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetPratilipiReviewListRequest extends GenericRequest {

	private Long pratilipiId;
	
	private String cursor;
	
	private Integer resultCount;

	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public String getCursor() {
		return cursor;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}
	
}
