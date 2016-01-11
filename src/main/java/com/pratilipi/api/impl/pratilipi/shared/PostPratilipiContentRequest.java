package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostPratilipiContentRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;

	@Validate( required = true )
	private Integer pageNo;
	
	@Validate( required = true )
	private String pageContent;

	private Boolean insertNew;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Integer getPageNumber() {
		return pageNo;
	}
	
	public Object getPageContent() {
		return pageContent;
	}

	public boolean getInsertNew() {
		return insertNew == null ? false : insertNew;
	}

}
