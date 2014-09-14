package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.UserQueryData;

public class UserQueryRequest implements IsSerializable {

	private UserQueryData userQueryData;


	@SuppressWarnings("unused")
	private UserQueryRequest() {}
	
	public UserQueryRequest( UserQueryData userQueryData ) {
		this.userQueryData = userQueryData;
	}
	
	
	public UserQueryData getUserQuery() {
		return userQueryData;
	}

}
