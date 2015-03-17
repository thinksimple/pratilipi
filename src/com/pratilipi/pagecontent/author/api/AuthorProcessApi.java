package com.pratilipi.pagecontent.author.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Post;
import com.claymus.api.shared.GenericRequest;
import com.claymus.api.shared.GenericResponse;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.taskqueue.Task;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.author.api.shared.AuthorProcessPostRequest;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/author/process" )
public class AuthorProcessApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( AuthorProcessApi.class.getName() );
	
	
	@Get
	public GenericResponse getAuthorProcess( GenericRequest request ) {
		
		if( ! ClaymusHelper.getSystemProperty( "domain" ).equals( "www.pratilipi.com" ) )
			return new GenericResponse();

		AuthorFilter authorFilter = new AuthorFilter();
		authorFilter.setNextProcessDateEnd( new Date() );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		List<Long> authorIdList = dataAccessor.getAuthorIdList( authorFilter, null, null ).getDataList();
		
		List<Task> taskList = new ArrayList<>( authorIdList.size() );
		for( Long authorId : authorIdList ) {
			Task task = TaskQueueFactory.newTask()
					.addParam( "authorId", authorId.toString() )
					.addParam( "updateStats", "true" )
					.setUrl( "/author/process" );
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
			AuthorContentHelper.updateAuthorSearchIndex( request.getAuthorId(), this.getThreadLocalRequest() );
			PratilipiContentHelper.updatePratilipiSearchIndex( null, request.getAuthorId(), this.getThreadLocalRequest() );
			boolean changed = AuthorContentHelper.createUpdateAuthorPageUrl( request.getAuthorId(), this.getThreadLocalRequest() );
			if( changed )
				PratilipiContentHelper.createUpdatePratilipiPageUrl( null, request.getAuthorId(), this.getThreadLocalRequest() );
		}
		
		if( request.updateStats() ) {
			boolean changed = AuthorContentHelper.updateAuthorStats( request.getAuthorId(), this.getThreadLocalRequest() );
			
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
			Author author = dataAccessor.getAuthor( request.getAuthorId() );
			if( changed ) {
				author.setLastProcessDate( new Date() );
				author.setNextProcessDate( new Date( new Date().getTime() + 3600000 ) ); // Now + 1 Hr
			} else {
				Long nextUpdateAfterMillis = 2 * ( new Date().getTime() - author.getLastProcessDate().getTime() );
				if( nextUpdateAfterMillis < 3600000L ) // 1 Hr
					nextUpdateAfterMillis = 3600000L;
				else if( nextUpdateAfterMillis > 604800000L ) // 1 Wk
					nextUpdateAfterMillis = 604800000L;
				author.setNextProcessDate( new Date( new Date().getTime() + nextUpdateAfterMillis ) );
			}
			author = dataAccessor.createOrUpdateAuthor( author );

			if( changed )
				AuthorContentHelper.updateAuthorSearchIndex( request.getAuthorId(), this.getThreadLocalRequest() );
		}
		
		return new GenericResponse();
	}

}