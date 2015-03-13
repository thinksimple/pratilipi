package com.pratilipi.pagecontent.pratilipi.api;

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
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.PratilipiProcessPostRequest;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/process" )
public class PratilipiProcessApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( PratilipiProcessApi.class.getName() );
	
	
	@Get
	public GenericResponse getPratilipiProcess( GenericRequest request ) {

		if( ! ClaymusHelper.getSystemProperty( "domain" ).equals( "www.pratilipi.com" ) )
			return new GenericResponse();

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setNextUpdateEnd( new Date() );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		List<Long> pratilipiIdList = dataAccessor.getPratilipiIdList( pratilipiFilter, null, null ).getDataList();
		
		for( Long pratilipiId : pratilipiIdList ) {
			Task task = TaskQueueFactory.newTask()
					.addParam( "pratilipiId", pratilipiId.toString() )
					.addParam( "updateStats", "true" )
					.setUrl( "/pratilipi/process" );
			TaskQueueFactory.getPratilipiTaskQueue().add( task );
		}
		
		logger.log( Level.INFO, "Added " + pratilipiIdList.size() + " tasks." );
		
		return new GenericResponse();
	}
	
	@Post
	public GenericResponse postPratilipiProcess( PratilipiProcessPostRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		if( request.processData() ) {
			PratilipiContentHelper.updatePratilipiSearchIndex( request.getPratilipiId(), null, this.getThreadLocalRequest() );
		}
		
		if( request.processContent() ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
			Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );
			
			if( pratilipi.getType() == PratilipiType.BOOK || pratilipi.getType() == PratilipiType.MAGAZINE )
				PratilipiContentHelper.updatePratilipiIndex( request.getPratilipiId(), this.getThreadLocalRequest() );
		}
		
		if( request.updateStats() ) {
			PratilipiContentHelper.updatePratilipiStats( request.getPratilipiId(), this.getThreadLocalRequest() );
			PratilipiContentHelper.updatePratilipiSearchIndex( request.getPratilipiId(), null, this.getThreadLocalRequest() );
		}
		
		return new GenericResponse();
	}

}