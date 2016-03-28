package com.pratilipi.api.impl.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.GenericUserResponse;
import com.pratilipi.api.impl.user.shared.PostUserPasswordUpdateRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/passwordupdate" )
public class UserPasswordUpdateApi extends GenericApi {
	
	@Post
	public GenericUserResponse post( PostUserPasswordUpdateRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		UserData userData;
		if( request.getEmail() != null && request.getVerificationToken() != null ) {
			// Verifying email
			UserDataUtil.verifyUserEmail( request.getEmail(), request.getVerificationToken() );
			// Updating password
			UserDataUtil.updateUserPassword( request.getEmail(), request.getVerificationToken(), request.getNewPassword() );
			// Logging-in the user
			userData = UserDataUtil.loginUser( request.getEmail(), request.getVerificationToken() );
		} else if( request.getPassword() != null ) {
			// Updating password
			UserDataUtil.updateUserPassword( request.getPassword(), request.getNewPassword() );
			userData = UserDataUtil.getCurrentUser();
		} else {
			throw new InvalidArgumentException( GenericRequest.ERR_INSUFFICIENT_ARGS );
		}
		
		return new GenericUserResponse( userData );
		
	}
	
}