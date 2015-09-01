package com.pratilipi.api.user;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.user.shared.PutUserFacebookLoginRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;


@Bind( uri= "/user/facebook_login" )  
public class UserFacebookLoginApi extends GenericApi {
	
	private static final long serialVersionUID = 1L;
	private static final String FACEBOOK_SUFFIX = "@facebook.com";
	
	@Put
	public static UserResponse facebookLogin( PutUserFacebookLoginRequest putUserFacebookLoginRequest ) throws InvalidArgumentException, UnexpectedServerException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Gson gson = new Gson();
		
		//Validate the access token.
		if( ! FacebookApi.validateAccessToken( putUserFacebookLoginRequest.getUserId(), putUserFacebookLoginRequest.getAccessToken() ) ) 
				throw new InsufficientAccessException();
		
		// Register / Update User and login.
		UserData userData;
		String facebookEmail = putUserFacebookLoginRequest.getUserId() + FACEBOOK_SUFFIX;
		if( putUserFacebookLoginRequest.getEmailId() == null )
			putUserFacebookLoginRequest.setEmailId( facebookEmail );
		
		if( dataAccessor.getUserByEmail( putUserFacebookLoginRequest.getEmailId() ) == null && dataAccessor.getUserByFacebookId( putUserFacebookLoginRequest.getUserId() ) == null ) 
				userData = UserDataUtil.registerFacebookUser( putUserFacebookLoginRequest, UserSignUpSource.WEBSITE_FACEBOOK ) ;
		else 
				userData = UserDataUtil.updateFacebookUser( putUserFacebookLoginRequest );
		
		userData = UserDataUtil.loginFacebookUser( putUserFacebookLoginRequest );
		
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}

}
