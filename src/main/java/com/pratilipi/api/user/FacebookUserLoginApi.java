package com.pratilipi.api.user;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.user.shared.FacebookUserData;
import com.pratilipi.api.user.shared.FacebookValidation;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

import freemarker.template.TemplateException;

@Bind( uri= "/user/facebook_login" )  
public class FacebookUserLoginApi extends GenericApi {
	
	private static final long serialVersionUID = 1L;
	private static final String FACEBOOK_SUFFIX = "@facebook.com";
	private static final String APP_PROPERTY_ID = "Facebook.Credentials";
	
	@Put
	public static UserResponse facebookLogin( FacebookUserData facebookUserData ) throws InvalidArgumentException, UnexpectedServerException, MessagingException, IOException, TemplateException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Gson gson = new Gson();
		
		//Validate the access token.
		Map< String, String > facebookCredentials = dataAccessor.getAppProperty( APP_PROPERTY_ID ).getValue();
		FacebookValidation facebookValidation = new FacebookValidation( facebookCredentials );
		
		if( facebookValidation.validateAccessToken( facebookUserData.getUserId(), facebookUserData.getAccessToken() ) == false ) 
			return gson.fromJson( gson.toJson( new UserData( Long.parseLong( facebookUserData.getUserId() ) ) ), UserResponse.class );
		
		// Register / Update User and login.
		
		/* Algorithm
		 * EMAIL - Either facebookEmail or originalEmail
		 * if( userNotPresentByEmail or UserNotPresentByFacebookId ) 
		 * 		loginUser;
		 * else 
		 * 		updateUser;
		 * loginUser;
		 * 
		 */
		
		UserData userData;
		String facebookEmail = facebookUserData.getUserId() + FACEBOOK_SUFFIX;
		if( facebookUserData.getEmailId() == null )
			facebookUserData.setEmailId( facebookEmail );
		
		if( dataAccessor.getUserByEmail( facebookUserData.getEmailId() ) == null && dataAccessor.getUserByFacebookId( facebookUserData.getUserId() ) == null ) 
				userData = UserDataUtil.registerFacebookUser( facebookUserData, UserSignUpSource.WEBSITE_FACEBOOK ) ;
		else 
				userData = UserDataUtil.updateFacebookUser( facebookUserData );
		
		userData = UserDataUtil.loginFacebookUser( facebookUserData );
		
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}

}
