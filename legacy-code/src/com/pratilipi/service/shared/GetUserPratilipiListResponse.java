package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.UserPratilipiData;

public class GetUserPratilipiListResponse implements IsSerializable {
	
	private List<UserPratilipiData> userPratilipiDataList;
	
	
	@SuppressWarnings("unused")
	private GetUserPratilipiListResponse(){}
	
	public GetUserPratilipiListResponse( List<UserPratilipiData> userPratilipiDataList ){
		this.userPratilipiDataList = userPratilipiDataList;
	}

	
	public List<UserPratilipiData> getUserPratilipiList(){
		return userPratilipiDataList;
	}
	
}
