package com.pratilipi.api.user;

import java.io.IOException;

import javax.mail.MessagingException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.user.shared.PutUserRegisterRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

import freemarker.template.TemplateException;

@SuppressWarnings("serial")
@Bind( uri= "/user/register" )
public class UserRegisterApi extends GenericApi {

	@Put
	public UserResponse put( PutUserRegisterRequest request )
			throws InvalidArgumentException, UnexpectedServerException, MessagingException, IOException, TemplateException {

		JsonObject errorMessages = new JsonObject();
		if( ! request.getPassword().equals( request.getPassword2() ) ) {
			errorMessages.addProperty( "password2", "Must be same as password." );
			throw new InvalidArgumentException( errorMessages );
		}

		
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
		Task task3 = TaskQueueFactory.newTask()
				.setUrl( "/user/process" )
				.addParam( "userId", userData.getId().toString() )
				.addParam( "createAuthorProfile", "true" );
		TaskQueueFactory.getUserTaskQueue().addAll( task1, task2, task3 );

		
		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}
	
}
