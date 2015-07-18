package com.pratilipi.api.user;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/logout" )
public class UserLogoutApi extends GenericApi {

	@Get
	public UserResponse logout( GenericRequest request )
			throws InvalidArgumentException {
		
		Gson gson = new Gson();
		UserData userData = UserDataUtil.logoutUser();
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}
	
}
