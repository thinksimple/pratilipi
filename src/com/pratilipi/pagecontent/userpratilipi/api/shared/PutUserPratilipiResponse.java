package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.service.shared.data.UserPratilipiData;

@SuppressWarnings( "serial" )
public class PutUserPratilipiResponse extends GenericResponse {
	
	private UserPratilipiData userPratilipiData;
	
	public PutUserPratilipiResponse( UserPratilipiData userPratilipiData ){
		this.userPratilipiData = userPratilipiData;
	}
	
	public UserPratilipiData getUserPratilipiData(){
		return userPratilipiData;
	}
}
