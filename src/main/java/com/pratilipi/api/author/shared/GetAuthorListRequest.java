package com.pratilipi.api.author.shared;

import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Language;

public class GetAuthorListRequest extends GenericRequest {

	private Language language;

	private Boolean orderByContentPublished;

	private String cursor;
	
	private Integer resultCount;
	
	
	public Language getLanguage(){
		return language;
	}
	
	public Boolean getOrderByContentPublished() {
		return orderByContentPublished;
	}

	public String getCursor() {
		return cursor;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}
	
}
