package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SaveAuthorResponse implements IsSerializable {

	private Long authorId;

	
	@SuppressWarnings("unused")
	private SaveAuthorResponse() {}
	
	public SaveAuthorResponse( Long authorId ) {
		this.authorId = authorId;
	}
	
	
	public Long getAuthorId() {
		return this.authorId;
	}
	
}
