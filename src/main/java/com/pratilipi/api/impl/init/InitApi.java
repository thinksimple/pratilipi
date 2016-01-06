package com.pratilipi.api.impl.init;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetInitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.type.User;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) {
		
/*		
		List<Long> authorIdList = DataAccessorFactory.getDataAccessor()
				.getAuthorIdList( new AuthorFilter(), null, null )
				.getDataList();

		List<Task> taskList = new ArrayList<Task>( authorIdList.size() );
		for( Long authorId : authorIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", authorId.toString() )
					.addParam( "processData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getAuthorTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks in the queue." );
*/
		
/*		
		List<Long> pratilipiIdList = DataAccessorFactory.getDataAccessor()
				.getPratilipiIdList( new PratilipiFilter(), null, null )
				.getDataList();

		List<Task> taskList = new ArrayList<Task>( pratilipiIdList.size() );
		for( Long pratilipiId : pratilipiIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiId", pratilipiId.toString() )
					.addParam( "processData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks in the queue." );
*/
		
/*		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		String cursor = null;
		int count = 0;
		do {
			DataListCursorTuple<Author> authorListCursorTuple =
					dataAccessor.getAuthorList( new AuthorFilter(), cursor, 100 );
			List<Author> authorList = authorListCursorTuple.getDataList();
			cursor = authorListCursorTuple.getCursor();
			count = count + authorList.size();
			for( Author author : authorList ) {
				if( author.getUserId() != null ) {
					User user = dataAccessor.getUser( author.getUserId() );
					if( ! author.getEmail().equals( user.getEmail() ) )
						logger.log( Level.SEVERE, "Author email " + author.getEmail() + " doesn't match with user email " + user.getEmail() );
				}
			}
		} while( cursor != null );
		
		logger.log( Level.INFO, "Checked " + count + " author records." );
*/
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		String cursor = null;
		int count = 0;
		do {
			DataListCursorTuple<User> userListCursorTuple = dataAccessor.getUserList( cursor, 100 );
			List<User> userList = userListCursorTuple.getDataList();
			cursor = userListCursorTuple.getCursor();
			count = count + userList.size();
			for( User user : userList ) {
				user.getState();
				user.getSignUpSource();
				dataAccessor.createOrUpdateUser( user );
			}
		} while( cursor != null );
		
		logger.log( Level.INFO, "Checked " + count + " author records." );
		
		
		return new GenericResponse();
		
	}
	
}