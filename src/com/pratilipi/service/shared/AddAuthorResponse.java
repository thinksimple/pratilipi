package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddAuthorResponse implements IsSerializable {

	private Long authorId;

	
	@SuppressWarnings("unused")
	private AddAuthorResponse() {}
	
	public AddAuthorResponse( Long authorId ) {
		this.authorId = authorId;
	}
	
	
	public Long getAuthorId() {
		return this.authorId;
	}
	
}
