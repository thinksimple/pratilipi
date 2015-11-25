package com.pratilipi.api.impl.pratilipi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiProcessRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/process" )
public class PratilipiProcessApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( PratilipiProcessApi.class.getName() );
	
	
	@Get
	public GenericResponse getPratilipiProcess( GenericRequest request ) throws UnexpectedServerException {

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setNextProcessDateEnd( new Date() );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Long> pratilipiIdList = dataAccessor.getPratilipiIdList( pratilipiFilter, null, null ).getDataList();
		
		for( int i = 0; i < pratilipiIdList.size(); i = i + 100 ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiIdList", new Gson().toJson( pratilipiIdList.subList( i, i + 100 < pratilipiIdList.size() ? i + 100 : pratilipiIdList.size() ) ) )
					.addParam( "updateStats", "true" );
			TaskQueueFactory.getPratilipiTaskQueue().add( task );
		}
		
		logger.log( Level.INFO, "Created task(s) with " + pratilipiIdList.size() + " Pratilipi ids." );

		return new GenericResponse();
	}
	
	@Post
	public GenericResponse postPratilipiProcess( PostPratilipiProcessRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		List<Long> pratilipiIdList = request.getPratilipiIdList() != null ? request.getPratilipiIdList() : new ArrayList<Long>( 1 );
		if( request.getPratilipiId() != null )
			pratilipiIdList.add( request.getPratilipiId() );
		
		if( pratilipiIdList.size() == 0 ) {
			logger.log( Level.SEVERE, "0 pratilipiId found." );
			throw new InvalidArgumentException( "'pratilipiId' or 'pratilipiIdList' must be set/non-empty." );
		}
		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		
		if( request.processData() ) {
			for( Long pratilipiId : pratilipiIdList )
				PratilipiDataUtil.createOrUpdatePratilipiPageUrl( pratilipiId );
			PratilipiDataUtil.updatePratilipiSearchIndex( pratilipiIdList );
			PratilipiDataUtil.updateFacebookScrape( pratilipiIdList );
		}
		
		if( request.processCover() ) { }
		
		if( request.processContent() ) {
			for( Long pratilipiId : pratilipiIdList ) {
				PratilipiDataUtil.updatePratilipiIndex( pratilipiId );
				
				boolean changed = PratilipiDataUtil.updatePratilipiKeywords( pratilipiId );
				if( changed )
					PratilipiDataUtil.updatePratilipiSearchIndex( pratilipiId, null );
			}
		}
		
		if( request.updateStats() ) {
			List<Long> updatedIds = PratilipiDataUtil.updatePratilipiStats( pratilipiIdList );
			
			for( Long pratilipiId : pratilipiIdList ) {
				Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
				if( updatedIds.contains( pratilipiId ) ) {
					pratilipi.setLastProcessDate( new Date() );
					pratilipi.setNextProcessDate( new Date( new Date().getTime() + 900000L ) ); // Now + 15 Min
				} else {
					Long nextUpdateAfterMillis = 2 * ( new Date().getTime() - pratilipi.getLastProcessDate().getTime() );
					if( nextUpdateAfterMillis < 900000L ) // 15 Min
						nextUpdateAfterMillis = 900000L;
					else if( nextUpdateAfterMillis > 86400000L ) // 1 Day
						nextUpdateAfterMillis = 86400000L;
					pratilipi.setNextProcessDate( new Date( new Date().getTime() + nextUpdateAfterMillis ) );
				}
				pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
			}
			
			PratilipiDataUtil.updatePratilipiSearchIndex( updatedIds );
		}
		
		return new GenericResponse();
	}

}