package com.pratilipi.api.impl.userpratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class GetUserPratilipiRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

}
