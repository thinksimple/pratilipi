package com.pratilipi.api.impl.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.GenericUserResponse;
import com.pratilipi.api.impl.user.shared.PostUserRegisterRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user/register" )
public class UserRegisterApi extends GenericApi {

	@Post
	public GenericUserResponse post( PostUserRegisterRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		String firstName = request.getName().trim();
		String lastName = null;
		String email = request.getEmail().trim().toLowerCase();

		if( firstName.lastIndexOf( ' ' ) != -1 ) {
			lastName = firstName.substring( firstName.lastIndexOf( ' ' ) + 1 );
			firstName = firstName.substring( 0, firstName.lastIndexOf( ' ' ) );
		}
		

		// Register the User.
		UserData userData = UserDataUtil.registerUser( firstName, lastName,
				email, request.getPassword(),
				UserDataUtil.getUserSignUpSource( false, false ) );
		// Create Author profile for the User.
		Long authorId = AuthorDataUtil.createAuthorProfile( userData, UxModeFilter.getFilterLanguage() );
		// Log-in the User.
		userData = UserDataUtil.loginUser( email, request.getPassword() );

		
		Task task1 = TaskQueueFactory.newTask()
				.setUrl( "/user/email" )
				.addParam( "userId", userData.getId().toString() )
				.addParam( "language", UxModeFilter.getDisplayLanguage().toString() )
				.addParam( "sendWelcomeMail", "true" );
		Task task2 = TaskQueueFactory.newTask()
				.setUrl( "/user/email" )
				.addParam( "userId", userData.getId().toString() )
				.addParam( "language", UxModeFilter.getDisplayLanguage().toString() )
				.addParam( "sendEmailVerificationMail", "true" );
		Task task3 = TaskQueueFactory.newTask()
				.setUrl( "/author/process" )
				.addParam( "authorId", authorId.toString() )
				.addParam( "processData", "true" );

		TaskQueueFactory.getUserTaskQueue().addAll( task1, task2, task3 );

		
		return new GenericUserResponse( userData );
		
	}
	
}
