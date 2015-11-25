package com.pratilipi.api.impl.userpratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class GetUserPratilipiReviewListRequest extends GenericRequest {

	@Validate( required = true )
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
