package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetBookRequest implements IsSerializable {
	
	private Long bookId;
	
	
	@SuppressWarnings("unused")
	private GetBookRequest() {}
	
	public GetBookRequest( Long bookId ) {
		this.bookId = bookId;
	}
	
	
	public Long getBookId() {
		return this.bookId;
	}

}
