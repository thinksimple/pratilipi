package com.pratilipi.api.user;

import java.util.Date;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.user.shared.PutUserFacebookLoginRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user/login/facebook" )  
public class UserFacebookLoginApi extends GenericApi {
	
	@Put
	public static UserResponse facebookLogin( PutUserFacebookLoginRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		UserData userData = UserDataUtil.loginUser(
				request.getFbUserAccessToken(),
				UserSignUpSource.WEBSITE_FACEBOOK ); // TODO: Facebook SignUp on Android ?

		if( new Date().getTime() - userData.getSignUpDate().getTime() <= 60000
				&& userData.getEmail() != null ) {
			
			Task task1 = TaskQueueFactory.newTask()
					.setUrl( "/user/email" )
					.addParam( "userId", userData.getId().toString() )
					.addParam( "sendWelcomeMail", "true" );
			Task task2 = TaskQueueFactory.newTask()
					.setUrl( "/user/process" )
					.addParam( "userId", userData.getId().toString() )
					.addParam( "createAuthorProfile", "true" );
			TaskQueueFactory.getUserTaskQueue().addAll( task1, task2 );
			
		}	
		
		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}

}
