package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericRequest;
import com.pratilipi.commons.shared.PratilipiContentType;

@SuppressWarnings("serial")
public class GetPratilipiContentRequest extends GenericRequest {

	private Long pratilipiId;
	private Integer pageNo;
	private PratilipiContentType contentType;
	

	private GetPratilipiContentRequest() {
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

}
