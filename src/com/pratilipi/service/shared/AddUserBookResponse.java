package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddUserBookResponse implements IsSerializable {

	private String userBookId;

	
	@SuppressWarnings("unused")
	private AddUserBookResponse() {}
	
	public AddUserBookResponse( String userBookId ) {
		this.userBookId = userBookId;
	}
	
	public String getUserBookId() {
		return this.userBookId;
	}
	
}
