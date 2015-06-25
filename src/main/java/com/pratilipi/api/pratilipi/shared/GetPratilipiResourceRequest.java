package com.pratilipi.api.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class GetPratilipiResourceRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;

	@Validate( required = true )
	private String name;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public String getName() {
		return name;
	}
	
}
