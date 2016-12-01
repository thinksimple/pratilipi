package com.pratilipi.api.impl.email;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailState;
import com.pratilipi.common.type.EmailType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.UserAuthorDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/email" )
public class EmailApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L )
		private Long emailId;

	}

	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<Email> emailList = dataAccessor.getEmailList( null, null, null, EmailState.PENDING, null );
		List<Task> taskList = new ArrayList<>( emailList.size() );

		for( Email email : emailList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/email" )
					.addParam( "emailId", email.getId().toString() );
			taskList.add( task );
		}

		TaskQueueFactory.getEmailOfflineTaskQueue().addAll( taskList );

		return new GenericResponse();
		
	}

	@Post
	public GenericResponse post( PostRequest request )
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Email email = dataAccessor.getEmail( request.emailId );

		if( email.getState() == EmailState.SENT )
			return new GenericResponse();

		if( email.getType() == EmailType.PRATILIPI_PUBLISHED_AUTHOR_EMAIL ) {
			AuthorDataUtil.sendContentPublishedMail( email.getPrimaryContentId(), 
														email.getUserId(), 
														email.getType() );

		} else if( email.getType() == EmailType.PRATILIPI_PUBLISHED_FOLLOWER_EMAIL ) {
			UserAuthorDataUtil.sendContentPublishedMail( email.getPrimaryContentId(),
															email.getUserId(),
															email.getType() );
		}
		

		email.setState( EmailState.SENT );
		dataAccessor.createOrUpdateEmail( email );

		return new GenericResponse();

	}		

}
