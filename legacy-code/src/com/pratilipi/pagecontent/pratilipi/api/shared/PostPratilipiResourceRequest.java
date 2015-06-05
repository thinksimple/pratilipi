package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericFileUploadRequest;

@SuppressWarnings("serial")
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
