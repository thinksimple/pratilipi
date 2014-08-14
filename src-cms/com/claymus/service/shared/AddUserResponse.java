package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddUserResponse implements IsSerializable {
	
	private Long userId;

	
	@SuppressWarnings("unused")
	private AddUserResponse() {}
	
	public AddUserResponse( Long userId ) {
		this.userId = userId;
	}
	
	
	public Long getUserId() {
		return this.userId;
	}
	
}
