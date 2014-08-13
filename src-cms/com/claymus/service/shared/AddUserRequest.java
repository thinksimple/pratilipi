package com.claymus.service.shared;

import com.claymus.service.shared.data.UserData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class AddUserRequest implements IsSerializable {

	private UserData userData;


	@SuppressWarnings("unused")
	private AddUserRequest() {}
	
	public AddUserRequest( UserData useData ) {
		this.userData = useData;
	}
	
	
	public UserData getUser() {
		return this.userData;
	}

}
