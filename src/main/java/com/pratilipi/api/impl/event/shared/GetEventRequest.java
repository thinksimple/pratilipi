package com.pratilipi.api.impl.event.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class GetEventRequest extends GenericRequest {

	@Validate( required = true, minLong = 1L )
	private Long eventId;
	
	
	public Long getEventId() {
		return eventId;
	}
	
}