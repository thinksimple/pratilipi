package com.pratilipi.api.init;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.init.shared.GetInitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		DataListCursorTuple<Long> authorIdListCursorTuple;
		do {
			
			authorIdListCursorTuple =
					dataAccessor.getAuthorIdList( new AuthorFilter(), null, 1000 );
			
			List<Task> taskList = new ArrayList<>( authorIdListCursorTuple.getDataList().size() );
			for( Long authorId : authorIdListCursorTuple.getDataList() ) {
				Task task = TaskQueueFactory.newTask()
						.setUrl( "/author/process" )
						.addParam( "authorId", authorId.toString() )
						.addParam( "processData", "true" );
				taskList.add( task );
			}
			TaskQueueFactory.getAuthorTaskQueue().addAll( taskList );
			
			logger.log( Level.INFO, "Added " + taskList.size() + " tasks." );
		
		} while( authorIdListCursorTuple.getCursor() != null && authorIdListCursorTuple.getDataList().size() == 1000 );
		
		return new GenericResponse();
		
	}
	
}