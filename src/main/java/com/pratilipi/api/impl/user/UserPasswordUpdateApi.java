package com.pratilipi.api.impl.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.PostUserPasswordUpdateRequest;
import com.pratilipi.api.impl.user.shared.UserResponse;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/passwordupdate" )
public class UserPasswordUpdateApi extends GenericApi {
	
	@Post
	public static UserResponse post( PostUserPasswordUpdateRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		if( ! request.getNewPassword().equals( request.getNewPassword2() ) ) {
			JsonObject errorMessages = new JsonObject();
			errorMessages.addProperty( "password2", GenericRequest.ERR_PASSWORD2_MISMATCH );
			throw new InvalidArgumentException( errorMessages );
		}
		
		UserData userData;
		if( request.getEmail() != null && request.getVerificationToken() != null ) {
			// Verifying email
			UserDataUtil.verifyUserEmail( request.getEmail(), request.getVerificationToken() );
			// Updating password
			UserDataUtil.updateUserPassword( request.getEmail(), request.getVerificationToken(), request.getNewPassword() );
			// Logging-in the user
			userData = UserDataUtil.loginUser(
					request.getEmail(),
					request.getVerificationToken() != null ? request.getVerificationToken() : request.getNewPassword() );
		} else if( request.getPassword() != null ) {
			UserDataUtil.updateUserPassword( request.getPassword(), request.getNewPassword() );
			userData = UserDataUtil.getCurrentUser();
			
		} else {
			throw new InvalidArgumentException( GenericRequest.ERR_INSUFFICIENT_ARGS );
		}
		
		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}
	
}