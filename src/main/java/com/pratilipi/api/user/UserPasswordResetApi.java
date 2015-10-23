package com.pratilipi.api.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.api.user.shared.PutUserPasswordResetRequest;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.User;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user/password/reset" )
public class UserPasswordResetApi extends GenericApi {
	
	@Put
	public static GenericResponse put( PutUserPasswordResetRequest request )
			throws UnexpectedServerException, InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( request.getEmail() );
		
		if( user == null ) 
			throw new InvalidArgumentException( "Email is not registered !" );
		
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/user/email" )
				.addParam( "userId", user.getId().toString() )
				.addParam( "sendPasswordResetMail", "true" );
		TaskQueueFactory.getUserTaskQueue().add( task );
		
		return new GenericResponse();
	}
}
