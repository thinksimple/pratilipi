package com.pratilipi.api.user;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.user.shared.PutUserLoginRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/login" )
public class UserLoginApi extends GenericApi {

	@Put
	public UserResponse login( PutUserLoginRequest request )
			throws InvalidArgumentException {
		
		Gson gson = new Gson();
		UserData userData = UserDataUtil.loginUser(
				request.getEmail(),
				request.getPassword() );
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}
	
}
