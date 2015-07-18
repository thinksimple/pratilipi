package com.pratilipi.api.pratilipi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.pratilipi.common.util.SystemProperty;
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

		if( SystemProperty.get( "cron" ).equals( "stop" ) )
			return new GenericResponse();

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setNextProcessDateEnd( new Date() );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Long> pratilipiIdList = dataAccessor.getPratilipiIdList( pratilipiFilter, null, 100 ).getDataList();
		
		List<Task> taskList = new ArrayList<>( pratilipiIdList.size() );
		for( Long pratilipiId : pratilipiIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiId", pratilipiId.toString() )
					.addParam( "updateStats", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiTaskQueue().addAll( taskList );
		
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks." );
		
		
		pratilipiIdList = dataAccessor.getPratilipiIdList( pratilipiFilter, null, 90 ).getDataList();
		long[] pratilipiIdListArray = new long[ pratilipiIdList.size() ];
		for( int iterator = 0; iterator < pratilipiIdList.size(); iterator ++ ) {
			pratilipiIdListArray [iterator] = pratilipiIdList.get( iterator );
		}
		PratilipiDataUtil.updatePratilipiStats( pratilipiIdListArray );
		logger.log( Level.INFO, "Added " + pratilipiIdList.size() + " id's for read count updation." );
		return new GenericResponse();
	}
	
	@Post
	public GenericResponse postPratilipiProcess( PostPratilipiProcessRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		if( request.processData() ) {
			PratilipiDataUtil.updatePratilipiSearchIndex( request.getPratilipiId(), null );
			PratilipiDataUtil.createOrUpdatePratilipiPageUrl( request.getPratilipiId() );
		}
		
		if( request.processCover() ) { }
		
		if( request.processContent() ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );
			if( pratilipi.getType() == PratilipiType.BOOK || pratilipi.getType() == PratilipiType.MAGAZINE )
				PratilipiDataUtil.updatePratilipiIndex( request.getPratilipiId() );
			
			boolean changed = PratilipiDataUtil.updatePratilipiKeywords( request.getPratilipiId() );
			if( changed )
				PratilipiDataUtil.updatePratilipiSearchIndex( request.getPratilipiId(), null );
		}
		
		if( request.updateStats() ) {
			boolean changed = PratilipiDataUtil.updatePratilipiStats( request.getPratilipiId() );
			
			try {
				dataAccessor.beginTx();
				Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );
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
				PratilipiDataUtil.updatePratilipiSearchIndex( request.getPratilipiId(), null );
		}
		
		return new GenericResponse();
	}

}