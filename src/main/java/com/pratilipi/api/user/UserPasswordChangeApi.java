package com.pratilipi.api.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.user.shared.PostUserPasswordChangeRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/password/change" )
public class UserPasswordChangeApi extends GenericApi {
	
	@Post
	public static UserResponse get( PostUserPasswordChangeRequest request ) throws InvalidArgumentException, UnexpectedServerException {
		
		JsonObject errorMessages = new JsonObject();
		
		if( ! request.getPassword().equals( request.getPassword2() ) ) {
			errorMessages.addProperty( "password2", "Must be same as password." );
			throw new InvalidArgumentException( errorMessages );
		}
		
		// Verifying the user.
		if( request.getToken() != null )
			UserDataUtil.verifyUserEmail( request.getEmail(), request.getToken() );
		
		// Change Password Utility.
		UserDataUtil.changeUserPassword( 
				request.getEmail(), 
				request.getToken() != null ? request.getToken() : request.getCurrentPassword() , 
				request.getPassword() );
		
		UserData userData = UserDataUtil.loginUser( request.getEmail(), request.getToken() != null ? request.getToken() : request.getPassword() );
		
		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}
}