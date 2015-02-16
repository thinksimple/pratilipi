package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PratilipiProcessPostRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;

	private Boolean processData;
	private Boolean processContent;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public boolean processData() {
		return processData == null ? false : processData;
	}
	
	public boolean processContent() {
		return processContent == null ? false : processContent;
	}

}
