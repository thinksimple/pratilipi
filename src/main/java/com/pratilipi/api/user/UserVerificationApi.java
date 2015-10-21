package com.pratilipi.api.user;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.api.user.shared.PutUserVerificationRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/verification" )
public class UserVerificationApi extends GenericApi{
	
	@Put
	public  GenericResponse get( PutUserVerificationRequest request )
					throws InvalidArgumentException, UnexpectedServerException {
		
		UserData userData = UserDataUtil.verifyUser( request.getEmail(), request.getVerificationToken() );
		
		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
		
	}
}
