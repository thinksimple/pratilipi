package com.pratilipi.api.impl.user;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.GenericUserResponse;
import com.pratilipi.api.impl.user.shared.PostUserRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user" )
public class UserApi extends GenericApi {

	@Post
	public GenericUserResponse post( PostUserRequest request )
			throws InvalidArgumentException, InsufficientAccessException {

		UserData userData = new UserData( request.getId() );
		if( request.hasEmail() )
			userData.setEmail( request.getEmail() );
		if( request.hasPhone() )
			userData.setPhone( request.getPhone() );
		
		// Save UserData.
		userData = UserDataUtil.saveUserData( userData );
		
		
		List<Task> taskList = new LinkedList<>();
		
		
		if( request.getId() == null ) { // New user
			
			String firstName = request.getName().trim();
			String lastName = null;
			if( firstName.lastIndexOf( ' ' ) != -1 ) {
				lastName = firstName.substring( firstName.lastIndexOf( ' ' ) + 1 );
				firstName = firstName.substring( 0, firstName.lastIndexOf( ' ' ) );
			}
			
			userData.setFirstName( firstName );
			userData.setLastName( lastName );
			
			// Create Author profile for the User.
			Long authorId = AuthorDataUtil.createAuthorProfile( userData, UxModeFilter.getFilterLanguage() );
			
			userData.setProfilePageUrl( "/author/" + authorId );
			
			
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/user/email" )
					.addParam( "userId", userData.getId().toString() )
					.addParam( "language", UxModeFilter.getDisplayLanguage().toString() )
					.addParam( "sendWelcomeMail", "true" );
			taskList.add( task );
			
		}
		

		// Send verification mail if user sate is REGISTERED
		if( userData.getState() == UserState.REGISTERED ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/user/email" )
					.addParam( "userId", userData.getId().toString() )
					.addParam( "language", UxModeFilter.getDisplayLanguage().toString() )
					.addParam( "sendEmailVerificationMail", "true" );
			taskList.add( task );
		}
		
		
		TaskQueueFactory.getUserTaskQueue().addAll( taskList );
		
		return new GenericUserResponse( userData );
		
	}
	
}
