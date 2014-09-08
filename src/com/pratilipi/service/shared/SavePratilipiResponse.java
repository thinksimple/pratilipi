package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SavePratilipiResponse implements IsSerializable {
	
	private Long pratilipiId;

	
	@SuppressWarnings("unused")
	private SavePratilipiResponse() {}
	
	public SavePratilipiResponse( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}
	
	
	public Long getPratilipiId() {
		return this.pratilipiId;
	}

}
