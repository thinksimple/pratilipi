package com.pratilipi.api.impl.email;

import java.util.ArrayList;
import java.util.Date;
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
import com.pratilipi.data.DataIdListIterator;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.util.EmailDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/email/process" )
public class EmailProcessApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L )
		private Long emailId;

	}


	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {

		DataIdListIterator<Email> itr = DataAccessorFactory.getDataAccessor()
				.getEmailIdIteratorWithStatePending();

		List<Task> taskList = new ArrayList<>( 1000 );
		while( itr.hasNext() ) {
			
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/email/process" )
					.addParam( "emailId", itr.next().toString() );
			taskList.add( task );
			
			if( taskList.size() == 1000 || ! itr.hasNext() ) {
				TaskQueueFactory.getEmailOfflineTaskQueue().addAll( taskList );
				taskList.clear();
			}
			
		}

		return new GenericResponse();
		
	}

	
	@Post
	public GenericResponse post( PostRequest request )
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Email email = dataAccessor.getEmail( request.emailId );
		EmailState emailState = null;

		if( email.getState() == EmailState.SENT )
			return new GenericResponse();

		if( email.getType() == EmailType.PRATILIPI_PUBLISHED_AUTHOR_EMAIL ) {
			emailState = EmailDataUtil.sendPratilipiPublisedAuthorEmail( Long.parseLong( email.getPrimaryContentId() ), email.getUserId() );

		} else if( email.getType() == EmailType.PRATILIPI_PUBLISHED_FOLLOWER_EMAIL ) {
			emailState = EmailDataUtil.sendPratilipiPublishedFollowerEmail( Long.parseLong( email.getPrimaryContentId() ), email.getUserId() );

		} else if( email.getType() == EmailType.AUTHOR_FOLLOW_EMAIL ) {
			emailState = EmailDataUtil.sendAuthorFollowEmail( email.getPrimaryContentId(), email.getUserId() );
		}

		email.setState( emailState );
		email.setLastUpdated( new Date() );
		dataAccessor.createOrUpdateEmail( email );

		return new GenericResponse();

	}		

}
