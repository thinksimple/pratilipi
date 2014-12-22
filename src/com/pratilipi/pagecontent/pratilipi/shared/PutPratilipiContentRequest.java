package com.pratilipi.pagecontent.pratilipi.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;
import com.pratilipi.commons.shared.PratilipiContentType;

@SuppressWarnings("serial")
public class PutPratilipiContentRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;

	@Validate( required = true )
	private Integer pageNo;
	
	@Validate( required = true )
	private PratilipiContentType contentType;
	
	@Validate( required = true )
	private String pageContent;

	private Boolean insertNew;
	

	private PutPratilipiContentRequest() {
		super( null );
	}
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Integer getPageNumber() {
		return pageNo;
	}
	
	public PratilipiContentType getContentType() {
		return contentType;
	}
	
	public Object getPageContent() {
		return pageContent;
	}

	public boolean getInsertNew() {
		return insertNew == null ? false : insertNew;
	}

}
