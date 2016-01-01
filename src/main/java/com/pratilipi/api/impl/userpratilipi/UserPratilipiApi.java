package com.pratilipi.api.impl.userpratilipi;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.impl.userpratilipi.shared.GetUserPratilipiRequest;
import com.pratilipi.api.impl.userpratilipi.shared.GetUserPratilipiResponse;
import com.pratilipi.api.impl.userpratilipi.shared.PutUserPratilipiRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi" )
public class UserPratilipiApi extends GenericApi {
	
	@Get
	public GetUserPratilipiResponse getUserPratilipi( GetUserPratilipiRequest request ) {

		UserPratilipiData userPratilipiData =
				UserPratilipiDataUtil.getUserPratilipi( request.getPratilipiId() );

		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userPratilipiData ), GetUserPratilipiResponse.class );
		
	}		

	@Put
	public GetUserPratilipiResponse putUserPratilipi( PutUserPratilipiRequest request )
			throws InsufficientAccessException {

		Gson gson = new Gson();

		UserPratilipiData userPratilipiData = gson.fromJson( gson.toJson( request ), UserPratilipiData.class );
		userPratilipiData = UserPratilipiDataUtil.saveUserPratilipi( userPratilipiData );
		
		return gson.fromJson( gson.toJson( userPratilipiData ), GetUserPratilipiResponse.class );
		
	}		

}
