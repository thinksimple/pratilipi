package com.pratilipi.api.impl.user;

import java.util.Date;

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
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user/login/google" )  
public class UserLoginGoogleApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		@Validate( required = true )
		private String googleIdToken;

	}

	
	@Post
	public UserApi.Response post( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		UserData userData = UserDataUtil.loginGoogleUser(
				request.googleIdToken,
				UserDataUtil.getUserSignUpSource( false, true ) );


		if( new Date().getTime() - userData.getSignUpDate().getTime() <= 60000 ) {

			AuthorDataUtil.createAuthorProfile( userData, UxModeFilter.getFilterLanguage() );
			userData = UserDataUtil.getCurrentUser();

			Task welcomeMailTask = TaskQueueFactory.newTask()
					.setUrl( "/user/email" )
					.addParam( "userId", userData.getId().toString() )
					.addParam( "language", UxModeFilter.getDisplayLanguage().toString() )
					.addParam( "sendWelcomeMail", "true" );

			TaskQueueFactory.getUserTaskQueue().add(  welcomeMailTask  );

		}

		return new UserApi.Response( userData, UserLoginGoogleApi.class );

	}

}
