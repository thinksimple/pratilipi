package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetUserBookListRequest implements IsSerializable {
	
	private Long bookId;
	
	
	@SuppressWarnings("unused")
	private GetUserBookListRequest() {}
	
	public GetUserBookListRequest( Long bookId ) {
		this.bookId = bookId;
	}
	
	
	public Long getBookId() {
		return this.bookId;
	}

}
