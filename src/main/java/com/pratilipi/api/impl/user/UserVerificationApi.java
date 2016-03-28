package com.pratilipi.api.impl.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.GenericUserResponse;
import com.pratilipi.api.impl.user.shared.PostUserVerificationRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/verification" )
public class UserVerificationApi extends GenericApi {
	
	@Post
	public GenericUserResponse post( PostUserVerificationRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		UserDataUtil.verifyUserEmail(
				request.getEmail(),
				request.getVerificationToken() );
		
		UserData userData = UserDataUtil.loginUser(
				request.getEmail(),
				request.getVerificationToken() );
		
		return new GenericUserResponse( userData );
		
	}
	
}