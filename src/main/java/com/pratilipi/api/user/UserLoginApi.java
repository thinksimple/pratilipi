package com.pratilipi.api.user;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.user.shared.PostUserLoginRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/login" )
public class UserLoginApi extends GenericApi {

	@Post
	public UserResponse login( PostUserLoginRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		Gson gson = new Gson();
		UserData userData = UserDataUtil.loginUser(
				request.getEmail(),
				request.getPassword() );
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}
	
}