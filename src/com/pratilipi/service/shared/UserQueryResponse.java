package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserQueryResponse implements IsSerializable {

	private String message;


	@SuppressWarnings("unused")
	private UserQueryResponse() {}
	
	public UserQueryResponse( String message ) {
		this.message = message;
	}

	
	public String getLanguageId() {
		return message;
	}
	
}
