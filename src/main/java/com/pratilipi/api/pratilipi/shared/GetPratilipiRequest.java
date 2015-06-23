package com.pratilipi.api.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class GetPratilipiRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;
	
	public Long getPratilipiId() {
		return pratilipiId;
	}
	
}