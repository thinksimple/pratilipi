package com.pratilipi.api.user;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.user.shared.PostUserLoginFacebookRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user/login/facebook" )  
public class UserLoginFacebookApi extends GenericApi {
	
	@Post
	public static UserResponse facebookLogin( PostUserLoginFacebookRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		UserData userData = UserDataUtil.loginUser(
				request.getFbUserAccessToken(),
				UserSignUpSource.WEBSITE_FACEBOOK ); // TODO: Facebook SignUp on Android ?

		List<Task> taskList = new LinkedList<>();
		

		Task fbValidationTask = TaskQueueFactory.newTask()
				.setUrl( "/user/facebook/validation" )
				.addParam( "pratilipiAccessToken", AccessTokenFilter.getAccessToken().getId() )
				.addParam( "fbAccessToken", request.getFbUserAccessToken() );
		
		taskList.add( fbValidationTask );

		
		if( new Date().getTime() - userData.getSignUpDate().getTime() <= 60000
				&& userData.getEmail() != null ) {
			
			Task welcomeMailTask = TaskQueueFactory.newTask()
					.setUrl( "/user/email" )
					.addParam( "userId", userData.getId().toString() )
					.addParam( "sendWelcomeMail", "true" );
			taskList.add( welcomeMailTask );
			
		}

		
		TaskQueueFactory.getUserTaskQueue().addAll( taskList );

		
		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}

}
