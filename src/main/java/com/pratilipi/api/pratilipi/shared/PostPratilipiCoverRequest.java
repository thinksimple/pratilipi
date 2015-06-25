package com.pratilipi.api.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericFileUploadRequest;

public class PostPratilipiCoverRequest extends GenericFileUploadRequest {

	@Validate( required = true )
	private Long pratilipiId;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

}
