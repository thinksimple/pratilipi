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
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListIterator;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.util.EmailDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/email/process" )
public class EmailProcessApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {

		@Validate( minLong = 1L )
		private Long emailId;

		@Validate( minLong = 1L )
		private Long userId;

	}


	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		DataListIterator<Email> itr = dataAccessor.getEmailIteratorWithStatePending();

		List<Task> taskList = new ArrayList<>( 1000 );
		List<Email> emailList = new ArrayList<>( 1000 );

		while( itr.hasNext() ) {

			Email email = itr.next();
			email.setState( EmailState.IN_PROGRESS );
			email.setLastUpdated( new Date() );
			emailList.add( email );

			Task task = TaskQueueFactory.newTask()
					.setUrl( "/email/process" )
					.addParam( "emailId", email.getId().toString() );
			taskList.add( task );

			if( taskList.size() == 1000 || ! itr.hasNext() ) {
				TaskQueueFactory.getEmailOfflineTaskQueue().addAll( taskList );
				taskList.clear();

				dataAccessor.createOrUpdateEmailList( emailList );
				emailList.clear();

			}

		}

		return new GenericResponse();
		
	}

	
	@Post
	public GenericResponse post( PostRequest request )
			throws UnexpectedServerException {

		if( request.emailId != null )
			EmailDataUtil.sendEmail( request.emailId );

		if( request.userId != null )
			EmailDataUtil.sendConsolidatedEmail( request.userId );

		return new GenericResponse();

	}		

}
