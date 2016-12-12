package com.pratilipi.api.impl.user;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
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
	
	public static class PostRequest extends GenericRequest {
		
		@Validate( required = true )
		private String fbUserAccessToken;
		
		
		public String getFbUserAccessToken() {
			return this.fbUserAccessToken;
		}

	}
	
	@Post
	public UserApi.Response post( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		UserData userData = UserDataUtil.loginFacebookUser(
				request.getFbUserAccessToken(),
				UserDataUtil.getUserSignUpSource( true, false ) );

		
		List<Task> taskList = new LinkedList<>();
		

		Task fbValidationTask = TaskQueueFactory.newTask()
				.setUrl( "/user/facebook/validation" )
				.addParam( "pratilipiAccessToken", AccessTokenFilter.getAccessToken().getId() )
				.addParam( "fbAccessToken", request.getFbUserAccessToken() );
		taskList.add( fbValidationTask );

		
		if( new Date().getTime() - userData.getSignUpDate().getTime() <= 60000 ) {
			
			AuthorDataUtil.createAuthorProfile( userData, UxModeFilter.getFilterLanguage() );
			userData = UserDataUtil.getCurrentUser();

			if( userData.getEmail() != null ) {
				Task welcomeMailTask = TaskQueueFactory.newTask()
						.setUrl( "/user/email" )
						.addParam( "userId", userData.getId().toString() )
						.addParam( "language", UxModeFilter.getDisplayLanguage().toString() )
						.addParam( "sendWelcomeMail", "true" );
				taskList.add( welcomeMailTask );
			}
			
		}

		
		TaskQueueFactory.getUserTaskQueue().addAll( taskList );

		
		return new UserApi.Response( userData, UserLoginFacebookApi.class );
	
	}

}
