package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetUserBookRequest implements IsSerializable {
	
	private Long bookId;
	private Long userId;
	
	
	@SuppressWarnings("unused")
	private GetUserBookRequest() {}
	
	public GetUserBookRequest( Long userId, Long bookId ) {
		this.bookId = bookId;
		this.userId = userId;
	}
	
	
	public Long getBookId() {
		return this.bookId;
	}
	
	public Long getUserId(){
		return this.userId;
	}

}
