package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddPratilipiResponse implements IsSerializable {

	private Long pratilipiId;

	
	@SuppressWarnings("unused")
	private AddPratilipiResponse() {}
	
	public AddPratilipiResponse( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}
	
	
	public Long getPratilipiId() {
		return this.pratilipiId;
	}
	
}
