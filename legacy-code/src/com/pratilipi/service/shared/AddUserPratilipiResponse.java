package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddUserPratilipiResponse implements IsSerializable {

	private String userPratilipiId;

	
	@SuppressWarnings("unused")
	private AddUserPratilipiResponse() {}
	
	public AddUserPratilipiResponse( String userPratilipiId ) {
		this.userPratilipiId = userPratilipiId;
	}
	
	public String getUserPratilipiId() {
		return this.userPratilipiId;
	}
	
}
