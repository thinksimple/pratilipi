package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.shared.GenericRequest;
import com.pratilipi.service.shared.data.UserPratilipiData;

@SuppressWarnings( "serial" )
public class PutUserPratilipiRequest extends GenericRequest {
	
	private UserPratilipiData userPratilipiData;
	
	public PutUserPratilipiRequest( UserPratilipiData userPratilipiData ){
		this.userPratilipiData = userPratilipiData;
	}
	
	public UserPratilipiData getUserPratilipiData(){
		return userPratilipiData;
	}
}
