package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.UserPratilipiData;

public class GetUserPratilipiResponse implements IsSerializable {
	
	private UserPratilipiData userPratilipiData;
	
	
	@SuppressWarnings("unused")
	private GetUserPratilipiResponse(){}
	
	public GetUserPratilipiResponse( UserPratilipiData userPratilipiData ){
		this.userPratilipiData = userPratilipiData;
	}

	
	public UserPratilipiData getUserPratilipi(){
		return this.userPratilipiData;
	}
	
}
