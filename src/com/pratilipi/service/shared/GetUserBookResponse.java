package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.UserBookData;

public class GetUserBookResponse implements IsSerializable {
	
	private UserBookData userBookData;
	
	
	@SuppressWarnings("unused")
	private GetUserBookResponse(){}
	
	public GetUserBookResponse( UserBookData userBookData ){
		this.userBookData = userBookData;
	}

	
	public UserBookData getBook(){
		return this.userBookData;
	}
	
}
