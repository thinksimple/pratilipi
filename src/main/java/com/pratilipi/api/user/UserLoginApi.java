package com.pratilipi.api.user;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.user.shared.UserLoginRequest;
import com.pratilipi.api.user.shared.UserLoginResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/login" )
public class UserLoginApi extends GenericApi {

	@Post
	public UserLoginResponse postUserLogin( UserLoginRequest request )
			throws InvalidArgumentException {
		
		Gson gson = new Gson();
		UserData userData = UserDataUtil.loginUser(
				request.getEmail(),
				request.getPassword() );
		return gson.fromJson( gson.toJson( userData ), UserLoginResponse.class );
	}
	
}
