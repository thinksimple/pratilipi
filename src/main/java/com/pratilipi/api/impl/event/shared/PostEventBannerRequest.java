package com.pratilipi.api.impl.event.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericFileUploadRequest;

public class PostEventBannerRequest extends GenericFileUploadRequest {

	@Validate( required = true )
	private Long eventId;
	

	public Long getEventId() {
		return eventId;
	}

}
