package com.pratilipi.api.init.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class GetInitRequest extends GenericRequest {

	@Validate( required = true )
	private String propertyName;
	
	
	public String getPropertyName() {
		return propertyName;
	}
	
}