package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.UserBookData;

public class AddUserBookRequest implements IsSerializable {

	private UserBookData userBookData;


	@SuppressWarnings("unused")
	private AddUserBookRequest() {}
	
	public AddUserBookRequest( UserBookData userBookData ) {
		this.userBookData = userBookData;
	}
	
	
	public UserBookData getUserBook() {
		return this.userBookData;
	}

}
