package com.pratilipi.api.user;

import java.util.Date;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.api.user.shared.PutUserVerificationRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.User;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/verification" )
public class UserVerificationApi extends GenericApi{
	
	@Put
	public  GenericResponse get( PutUserVerificationRequest request )
					throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( request.getEmail() );
		
		if( user == null ) 
			throw new InvalidArgumentException( "Invalid Email!" );
		
		if( user.getState().equals( UserState.ACTIVE ) ) 
			throw new InvalidArgumentException( "You are already verified! Please log in to continue." );
		
		if( user.getState().equals( UserState.BLOCKED ) ) 
			throw new InvalidArgumentException( "Sorry! Your Email ID " + request.getEmail() + " is blocked! For more information, mail us at contact@pratilipi.com" );
		
		if( user.getVerificationToken() == null ) 
			throw new InvalidArgumentException( "Token Expired!" );
		
		String verificationToken = user.getVerificationToken().substring( 0, user.getVerificationToken().indexOf( "|" ) );
		Long expiryDate = Long.parseLong( user.getVerificationToken().substring( user.getVerificationToken().indexOf( "|" ) + 1 ) );
		
		if( ! verificationToken.equals( request.getVerificationToken() ) ) 
			throw new InvalidArgumentException( "Invalid Token!" );
		
		if( new Date().getTime() > expiryDate ) 
			throw new InvalidArgumentException( "Token Expired!" );
		
		user.setState( UserState.ACTIVE );
		dataAccessor.createOrUpdateUser( user );
		UserData userData = UserDataUtil.loginUser( request.getEmail(), user.getVerificationToken() );
		
		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
		
	}
}