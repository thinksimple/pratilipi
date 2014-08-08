package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddPublisherResponse implements IsSerializable {
	
	private Long publisherId;

	
	@SuppressWarnings("unused")
	private AddPublisherResponse() {}
	
	public AddPublisherResponse( Long publisherId ) {
		this.publisherId = publisherId;
	}
	
	
	public Long getAuthorId() {
		return this.publisherId;
	}

}
