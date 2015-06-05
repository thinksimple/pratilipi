package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetPratilipiCoverRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;

	private Integer width;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Integer getWidth() {
		return width;
	}
	
}
