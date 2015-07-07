package com.pratilipi.api.author;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.author.shared.AuthorProcessPostRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/author/process" )
public class AuthorProcessApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( AuthorProcessApi.class.getName() );
	
	
	@Get
	public GenericResponse getAuthorProcess( GenericRequest request ) {
		
		if( SystemProperty.get( "cron" ).equals( "stop" ) )
			return new GenericResponse();

		AuthorFilter authorFilter = new AuthorFilter();
		authorFilter.setNextProcessDateEnd( new Date() );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Long> authorIdList = dataAccessor.getAuthorIdList( authorFilter, null, 100 ).getDataList();
		
		List<Task> taskList = new ArrayList<>( authorIdList.size() );
		for( Long authorId : authorIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", authorId.toString() )
					.addParam( "updateStats", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getAuthorTaskQueue().addAll( taskList );
		
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks." );
		
		return new GenericResponse();
	}
	
	@Post
	public GenericResponse postAuthorProcess( AuthorProcessPostRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		if( request.processData() ) {
			AuthorDataUtil.updateAuthorSearchIndex( request.getAuthorId() );
			PratilipiDataUtil.updatePratilipiSearchIndex( null, request.getAuthorId() );
			boolean changed = AuthorDataUtil.createOrUpdateAuthorPageUrl( request.getAuthorId() );
			if( changed ) {
				AuthorDataUtil.createOrUpdateAuthorDashboardPageUrl( request.getAuthorId() );
				// TODO: Update all Pratilipi's PageUrl ( where AUTHOR_ID == request.getAuthorId() )
			}
		}
		
		if( request.processImage() ) { }

		if( request.updateStats() ) {
			boolean changed = AuthorDataUtil.updateAuthorStats( request.getAuthorId() );
			
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			try {
				dataAccessor.beginTx();
				Author author = dataAccessor.getAuthor( request.getAuthorId() );
				if( changed )
					author.setLastProcessDate( new Date() );
				author.setNextProcessDate( new Date( new Date().getTime() + 3600000L ) ); // Now + 1 Hr
				author = dataAccessor.createOrUpdateAuthor( author );
				dataAccessor.commitTx();
			} finally {
				if( dataAccessor.isTxActive() )
					dataAccessor.rollbackTx();
			}
			
			if( changed )
				AuthorDataUtil.updateAuthorSearchIndex( request.getAuthorId() );
		}
		
		return new GenericResponse();
	}

}