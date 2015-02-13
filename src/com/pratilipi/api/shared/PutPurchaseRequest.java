package com.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PutPurchaseRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;
	
	@Validate( required = true, regEx = REGEX_EMAIL )
	private String userId;

	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public String getUserId() {
		return userId;
	}

}
