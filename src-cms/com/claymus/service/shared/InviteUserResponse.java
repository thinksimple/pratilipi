package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InviteUserResponse implements IsSerializable {
	
	private Long userId;

	
	@SuppressWarnings("unused")
	private InviteUserResponse() {}
	
	public InviteUserResponse( Long userId ) {
		this.userId = userId;
	}
	
	
	public Long getUserId() {
		return this.userId;
	}
	
}
