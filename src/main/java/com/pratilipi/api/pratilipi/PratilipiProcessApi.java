package com.pratilipi.api.pratilipi;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.pratilipi.shared.PostPratilipiProcessRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.PratilipiType;
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
		
		Gson gson = new Gson();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Long> pratilipiIdList = dataAccessor.getPratilipiIdList( pratilipiFilter, null, 100 ).getDataList();
		
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/pratilipi/process" )
				.addParam( "pratilipiIds", gson.toJson( pratilipiIdList ) )
				.addParam( "updateStats", "true" );
		
		TaskQueueFactory.getPratilipiTaskQueue().add( task );
		
		logger.log( Level.INFO, "Added " + pratilipiIdList.size() + " operations to one task." );
		
		try {
			Thread.sleep( 10 * 60 * 1000 );
		} catch (InterruptedException e) { }
		
		return new GenericResponse();
	}
	
	@Post
	public GenericResponse postPratilipiProcess( PostPratilipiProcessRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		if( request.processData() ) {
			for( Long pratilipiId : request.getPratilipiIds() ) {
				PratilipiDataUtil.updatePratilipiSearchIndex( pratilipiId, null );
				PratilipiDataUtil.createOrUpdatePratilipiPageUrl( pratilipiId );
			}
		}
		
		if( request.processCover() ) { }
		
		if( request.processContent() ) {
			for( Long pratilipiId : request.getPratilipiIds() ) {
				Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
				if( pratilipi.getType() == PratilipiType.BOOK || pratilipi.getType() == PratilipiType.MAGAZINE )
					PratilipiDataUtil.updatePratilipiIndex( pratilipiId );
				
				boolean changed = PratilipiDataUtil.updatePratilipiKeywords( pratilipiId );
				if( changed )
					PratilipiDataUtil.updatePratilipiSearchIndex( pratilipiId, null );
			}
		}
		
		if( request.updateStats() ) {
			Set<Long> updatedIds = PratilipiDataUtil.updatePratilipiStats( Arrays.asList( request.getPratilipiIds() ) );
			
			for( Long pratilipiId : request.getPratilipiIds() ) {
				Boolean changed = updatedIds.contains( pratilipiId ) ? true : false ;
				try {
					dataAccessor.beginTx();
					Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
					if( changed ) {
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
					dataAccessor.commitTx();
				} finally {
					if( dataAccessor.isTxActive() )
						dataAccessor.rollbackTx();
				}
				
				if( changed )
					PratilipiDataUtil.updatePratilipiSearchIndex( pratilipiId, null );
			}
		}
		
		return new GenericResponse();
	}

}