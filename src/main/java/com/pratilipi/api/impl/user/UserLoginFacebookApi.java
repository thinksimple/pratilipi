package com.pratilipi.api.impl.user;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.GenericUserResponse;
import com.pratilipi.api.impl.user.shared.PostUserLoginFacebookRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user/login/facebook" )  
public class UserLoginFacebookApi extends GenericApi {
	
	@Post
	public static GenericUserResponse facebookLogin( PostUserLoginFacebookRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		UserData userData = UserDataUtil.loginUser(
				request.getFbUserAccessToken(),
				UserDataUtil.getUserSignUpSource( true, false ) );

		
		List<Task> taskList = new LinkedList<>();
		

		Task fbValidationTask = TaskQueueFactory.newTask()
				.setUrl( "/user/facebook/validation" )
				.addParam( "pratilipiAccessToken", AccessTokenFilter.getAccessToken().getId() )
				.addParam( "fbAccessToken", request.getFbUserAccessToken() );
		taskList.add( fbValidationTask );

		
		if( new Date().getTime() - userData.getSignUpDate().getTime() <= 60000 ) {
			
			Long authorId = AuthorDataUtil.createAuthorProfile( userData, UxModeFilter.getFilterLanguage() );
			userData = UserDataUtil.getCurrentUser();

			Task authorProcessTask = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", authorId.toString() )
					.addParam( "processData", "true" );
			taskList.add( authorProcessTask );
			
			if( userData.getEmail() != null ) {
				Task welcomeMailTask = TaskQueueFactory.newTask()
						.setUrl( "/user/email" )
						.addParam( "userId", userData.getId().toString() )
						.addParam( "sendWelcomeMail", "true" );
				taskList.add( welcomeMailTask );
			}
			
		}

		
		TaskQueueFactory.getUserTaskQueue().addAll( taskList );

		
		return new GenericUserResponse( userData );
	
	}

}
