package com.pratilipi.api.impl.event.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetEventBannerRequest extends GenericRequest {

	private Long eventId;

	private Integer width;
	

	public Long getEventId() {
		return eventId;
	}

	public Integer getWidth() {
		return width;
	}
	
}
