package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PutPratilipiContentRequest extends GenericRequest {

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
