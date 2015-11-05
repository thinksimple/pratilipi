package com.pratilipi.api.user;

import java.util.Date;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.user.shared.PutUserLoginFacebookRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user/login/facebook" )  
public class UserLoginFacebookApi extends GenericApi {
	
	@Post
	public static UserResponse facebookLogin( PutUserLoginFacebookRequest request )
			throws InvalidArgumentException, UnexpectedServerException, InsufficientAccessException {
		
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
