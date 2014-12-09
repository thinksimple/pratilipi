package com.pratilipi.service.shared;

import com.pratilipi.commons.shared.GenericRequest;
import com.pratilipi.commons.shared.PratilipiContentType;

public class GetPratilipiContentRequest extends GenericRequest {

	private long pratilipiId;
	private int pageNo;
	private PratilipiContentType contentType;
	

	private GetPratilipiContentRequest() {
		super( null );
	}

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public int getPageNumber() {
		return pageNo;
	}
	
	public PratilipiContentType getContentType() {
		return contentType;
	}

}
