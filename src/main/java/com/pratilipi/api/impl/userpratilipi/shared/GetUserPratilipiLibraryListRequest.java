package com.pratilipi.api.impl.userpratilipi.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetUserPratilipiLibraryListRequest extends GenericRequest {

	private String cursor;
	private Integer resultCount;


	public String getCursor() {
		return cursor;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}
	
}
