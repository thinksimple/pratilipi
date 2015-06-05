package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
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
