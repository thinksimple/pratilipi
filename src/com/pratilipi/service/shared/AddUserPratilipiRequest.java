package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.UserPratilipiData;

public class AddUserPratilipiRequest implements IsSerializable {

	private UserPratilipiData userPratilipiData;


	@SuppressWarnings("unused")
	private AddUserPratilipiRequest() {}
	
	public AddUserPratilipiRequest( UserPratilipiData userPratilipiData ) {
		this.userPratilipiData = userPratilipiData;
	}
	
	
	public UserPratilipiData getUserPratilipi() {
		return this.userPratilipiData;
	}

}
