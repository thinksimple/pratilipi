package com.pratilipi.api.impl.user;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.PostUserRegisterRequest;
import com.pratilipi.api.impl.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user/register" )
public class UserRegisterApi extends GenericApi {

	@Post
	public UserResponse put( PostUserRegisterRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		String firstName = request.getName().trim();
		String lastName = null;
		String email = request.getEmail().trim().toLowerCase();

		if( firstName.lastIndexOf( ' ' ) != -1 ) {
			lastName = firstName.substring( firstName.lastIndexOf( ' ' ) + 1 );
			firstName = firstName.substring( 0, firstName.lastIndexOf( ' ' ) );
		}
		
		
		UserData userData = UserDataUtil.registerUser( firstName, lastName,
				email, request.getPassword(), request.getSignUpSource() );
		userData = UserDataUtil.loginUser( email, request.getPassword() );

		
		Task task1 = TaskQueueFactory.newTask()
				.setUrl( "/user/email" )
				.addParam( "userId", userData.getId().toString() )
				.addParam( "sendWelcomeMail", "true" );
		Task task2 = TaskQueueFactory.newTask()
				.setUrl( "/user/email" )
				.addParam( "userId", userData.getId().toString() )
				.addParam( "sendEmailVerificationMail", "true" );
		TaskQueueFactory.getUserTaskQueue().addAll( task1, task2 );

		
		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}
	
}
