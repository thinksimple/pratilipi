package com.pratilipi.api.impl.userpratilipi;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.userpratilipi.shared.GetUserPratilipiRequest;
import com.pratilipi.api.impl.userpratilipi.shared.GenericUserPratilipiResponse;
import com.pratilipi.api.impl.userpratilipi.shared.PostUserPratilipiRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi" )
public class UserPratilipiApi extends GenericApi {
	
	@Get
	public GenericUserPratilipiResponse getUserPratilipi( GetUserPratilipiRequest request ) {

		UserPratilipiData userPratilipiData =
				UserPratilipiDataUtil.getUserPratilipi( request.getPratilipiId() );

		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userPratilipiData ), GenericUserPratilipiResponse.class );
		
	}		

	@Post
	public GenericUserPratilipiResponse postUserPratilipi( PostUserPratilipiRequest request )
			throws InsufficientAccessException {

		Gson gson = new Gson();

		UserPratilipiData userPratilipiData = gson.fromJson( gson.toJson( request ), UserPratilipiData.class );
		userPratilipiData = UserPratilipiDataUtil.saveUserPratilipi( userPratilipiData );
		
		return gson.fromJson( gson.toJson( userPratilipiData ), GenericUserPratilipiResponse.class );
		
	}		

}
