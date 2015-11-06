package com.pratilipi.api.user;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.user.shared.PostUserVerificationRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/verification" )
public class UserVerificationApi extends GenericApi {
	
	@Post
	public UserResponse put( PostUserVerificationRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		Gson gson = new Gson();

		UserDataUtil.verifyUserEmail(
				request.getEmail(),
				request.getVerificationToken() );
		
		UserData userData = UserDataUtil.loginUser(
				request.getEmail(),
				request.getVerificationToken() );
		
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
		
	}
	
}