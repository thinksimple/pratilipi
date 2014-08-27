package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RegisterUserResponse implements IsSerializable {
	private Long userId;

	
	@SuppressWarnings("unused")
	private RegisterUserResponse() {}
	
	public RegisterUserResponse( Long userId ) {
		this.userId = userId;
	}
	
	
	public Long getUserId() {
		return this.userId;
	}
	
}
