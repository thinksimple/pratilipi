package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericFileUploadRequest;

public class PostPratilipiResourceRequest extends GenericFileUploadRequest {

	@Validate( required = true )
	private Long pratilipiId;

	private Boolean overwrite;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public boolean getOverWrite() {
		return overwrite == null ? false : overwrite;
	}
	
}
