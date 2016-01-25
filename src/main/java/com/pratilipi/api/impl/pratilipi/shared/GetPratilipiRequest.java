package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class GetPratilipiRequest extends GenericRequest {

	@Validate( required = true, minLong = 1L )
	private Long pratilipiId;
	
	
	public Long getPratilipiId() {
		return pratilipiId;
	}
	
}