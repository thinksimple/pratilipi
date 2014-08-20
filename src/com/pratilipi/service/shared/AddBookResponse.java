package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddBookResponse implements IsSerializable {

	private Long bookId;

	
	@SuppressWarnings("unused")
	private AddBookResponse() {}
	
	public AddBookResponse( Long bookId ) {
		this.bookId = bookId;
	}
	
	
	public Long getBookId() {
		return this.bookId;
	}
	
}
