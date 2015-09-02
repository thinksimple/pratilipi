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
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;


@Bind( uri= "/user/facebook_login" )  
public class UserFacebookLoginApi extends GenericApi {
	
	private static final long serialVersionUID = 1L;
	private static final String FACEBOOK_SUFFIX = "@facebook.com";
	
	@Put
	public static UserResponse facebookLogin( PutUserFacebookLoginRequest putUserFacebookLoginRequest ) throws InvalidArgumentException, UnexpectedServerException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		//Validate the access token.
		if( ! FacebookApi.validateAccessToken( putUserFacebookLoginRequest.getUserId(), putUserFacebookLoginRequest.getAccessToken() ) ) 
				throw new InsufficientAccessException();
		
		//User has not given e-mail permission
		if( putUserFacebookLoginRequest.getEmailId() == null )
			putUserFacebookLoginRequest.setEmailId( putUserFacebookLoginRequest.getUserId() + FACEBOOK_SUFFIX );
		
		//Check if the user is new user.
		Boolean newUser = true;
		if( dataAccessor.getUserByEmail( putUserFacebookLoginRequest.getEmailId() ) != null 
				|| dataAccessor.getUserByFacebookId( putUserFacebookLoginRequest.getUserId() ) != null )
			newUser = false;
		
		// Register / Update User and login.
		UserData userData = UserDataUtil.registerOrUpdateFacebookUser(
			putUserFacebookLoginRequest.getEmailId(), 
			putUserFacebookLoginRequest.getUserId(), 
			putUserFacebookLoginRequest.getFirstName(), 
			putUserFacebookLoginRequest.getLastName(), 
			putUserFacebookLoginRequest.getFullName(), 
			putUserFacebookLoginRequest.getGender(), 
			putUserFacebookLoginRequest.getBirthdayDate(), 
			UserSignUpSource.WEBSITE_FACEBOOK );
		
		userData = UserDataUtil.loginFacebookUser( putUserFacebookLoginRequest.getEmailId() );
		
		if( newUser ) {
			if( ! putUserFacebookLoginRequest.getEmailId().contains( FACEBOOK_SUFFIX ) ) {
				Task task1 = TaskQueueFactory.newTask()
						.setUrl( "/user/email" )
						.addParam( "userId", userData.getId().toString() )
						.addParam( "sendWelcomeMail", "true" );
				Task task2 = TaskQueueFactory.newTask()
						.setUrl( "/user/email" )
						.addParam( "userId", userData.getId().toString() )
						.addParam( "sendEmailVerificationMail", "true" );
				Task task3 = TaskQueueFactory.newTask()
						.setUrl( "/user/process" )
						.addParam( "userId", userData.getId().toString() )
						.addParam( "createAuthorProfile", "true" );
				TaskQueueFactory.getUserTaskQueue().addAll( task1, task2, task3 );
			}
		}	
		
		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}

}
