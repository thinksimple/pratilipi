package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericFileUploadRequest;

@SuppressWarnings("serial")
public class PostPratilipiCoverRequest extends GenericFileUploadRequest {

	@Validate( required = true )
	private Long pratilipiId;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

}
