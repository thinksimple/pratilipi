package com.pratilipi.api.impl.pratilipi;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/process" )
public class PratilipiProcessApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( PratilipiProcessApi.class.getName() );
	
	
	@Get
	public GenericResponse getPratilipiProcess( GenericRequest request )
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		
		// START: Creating ValidateData Tasks
		
		
		// Fetching AppProperty
		String appPropertyId = "Api.PratilipiProcess.ValidateData";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date( 0 ) );
		}

		// Fetching list of Pratilipi ids.
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setMinLastUpdate( (Date) appProperty.getValue(), false );
		List<Long> pratilipiIdList = dataAccessor.getPratilipiIdList( pratilipiFilter, null, 0, 10000 ).getDataList();

		// Creating one task per Pratilipi id.
		List<Task> taskList = new ArrayList<>( pratilipiIdList.size() );
		for( Long pratilipiId : pratilipiIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiId", pratilipiId.toString() )
					.addParam( "validateData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiOfflineTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " ValidateData tasks." );

		// Updating AppProperty.
		if( pratilipiIdList.size() > 0 ) {
			appProperty.setValue( dataAccessor.getPratilipi( pratilipiIdList.get( pratilipiIdList.size() - 1 ) ).getLastUpdated() );
			appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		}
		
		
		// END: Creating ValidateData Tasks

		
		// START: Creating UpdateStats Tasks
		
		
		pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setState( PratilipiState.PUBLISHED );
		pratilipiFilter.setMaxNextProcessDate( new Date(), true );
		
		pratilipiIdList = dataAccessor.getPratilipiIdList( pratilipiFilter, null, null, null ).getDataList();
		
		int batchSize = 80;
		taskList = new ArrayList<>( (int) Math.ceil( ( (double) pratilipiIdList.size() ) / batchSize  ) );
		for( int i = 0; i < pratilipiIdList.size(); i = i + batchSize ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiIdList", new Gson().toJson( pratilipiIdList.subList( i, i + batchSize < pratilipiIdList.size() ? i + batchSize : pratilipiIdList.size() ) ) )
					.addParam( "updateStats", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiOfflineTaskQueue().addAll( taskList );
		
		logger.log( Level.INFO, "Added " + taskList.size() + " UpdateStats task(s) with " + pratilipiIdList.size() + " Pratilipi ids." );

		
		// END: Creating UpdateStats Tasks
		
		
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
		
		if( request.validateData() ) {
			for( Long pratilipiId : pratilipiIdList ) {

				Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
				Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
				Page pratilipiPage = dataAccessor.getPage( PageType.PRATILIPI, pratilipiId );
				
				if( pratilipi.getLanguage() == null )
					throw new InvalidArgumentException( "Language is null." );
				
				if( pratilipi.getAuthorId() != null && author == null )
					throw new InvalidArgumentException( "Invalid Author id." );
				
				if( author != null && author.getState() == AuthorState.DELETED )
					throw new InvalidArgumentException( "Linked Author entity is DELETED." );
				
				if( pratilipi.getState() == PratilipiState.DELETED && pratilipiPage != null )
					throw new InvalidArgumentException( "DELETED Pratilipi has non-deleted Page entity." );
				
				if( pratilipi.getState() != PratilipiState.DELETED && pratilipiPage == null )
					throw new InvalidArgumentException( "Page entity is missing for the Pratilipi." );
				
			}
		}
		
		
		if( request.processData() ) {
			PratilipiDataUtil.updatePratilipiSearchIndex( pratilipiIdList );
			PratilipiDataUtil.updateFacebookScrape( pratilipiIdList );
		}

		
		if( request.processContent() ) {
			for( Long pratilipiId : pratilipiIdList ) {
				
				PratilipiDataUtil.updatePratilipiIndex( pratilipiId );
				
				boolean changed = PratilipiDataUtil.updatePratilipiKeywords( pratilipiId );
				if( changed )
					PratilipiDataUtil.updatePratilipiSearchIndex( pratilipiId, null );
				
			}
		}
		
		
		if( request.updateStats() ) {
			
			// Batch updating Pratilipi stats.
			List<Long> updatedIdList = PratilipiDataUtil.updatePratilipiStats( pratilipiIdList );
			
			// List of Author ids to be updated.
			List<Long> authorIdList = new LinkedList<Long>();

			// Updating Pratilipi.nextProcessDate
			for( Long pratilipiId : pratilipiIdList ) {
				Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
				
				Gson gson = new Gson();

				AccessToken accessToken = AccessTokenFilter.getAccessToken();
				AuditLog auditLog = dataAccessor.newAuditLogOfy();
				auditLog.setAccessId( accessToken.getId() );
				auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
				auditLog.setEventDataOld( gson.toJson( pratilipi ) );
				
				if( updatedIdList.contains( pratilipiId ) ) {
					pratilipi.setLastProcessDate( new Date() );
					pratilipi.setNextProcessDate( new Date( new Date().getTime() + 900000L ) ); // Now + 15 Min
					if( pratilipi.getAuthorId() != null )
						if( ! authorIdList.contains( pratilipi.getAuthorId() ) )
							authorIdList.add( pratilipi.getAuthorId() );
				} else {
					Long nextUpdateAfterMillis = 2 * ( new Date().getTime() - pratilipi.getLastProcessDate().getTime() );
					if( nextUpdateAfterMillis < 900000L ) // 15 Min
						nextUpdateAfterMillis = 900000L;
					else if( nextUpdateAfterMillis > 86400000L ) // 1 Day
						nextUpdateAfterMillis = 86400000L;
					pratilipi.setNextProcessDate( new Date( new Date().getTime() + nextUpdateAfterMillis ) );
				}
				
				auditLog.setEventDataNew( gson.toJson( pratilipi ) );
				
				pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog ); // TODO: Batch this call
			}

			// Batch updating search index.
			PratilipiDataUtil.updatePratilipiSearchIndex( updatedIdList );

			// Creating tasks to update author entities.
			List<Task> taskList = new ArrayList<>( authorIdList.size() );
			for( Long authorId : authorIdList ) {
				Task task = TaskQueueFactory.newTask()
						.setUrl( "/author/process" )
						.addParam( "authorId", authorId.toString() )
						.addParam( "updateStats", "true" );
				taskList.add( task );
			}
			if( request.getPratilipiId() != null && authorIdList.size() != 0 )
				TaskQueueFactory.getAuthorTaskQueue().add( taskList.get( 0 ) );
			else // Batch created by cron
				TaskQueueFactory.getAuthorOfflineTaskQueue().addAll( taskList );
			logger.log( Level.INFO, "Added " + taskList.size() + " AuthorProcessApi.updateStats tasks." );

		}
		
		
		if( request.updateUserPratilipiStats() ) {
			for( long pratilipiId : pratilipiIdList )
				PratilipiDataUtil.updateUserPratilipiStats( pratilipiId );
		}
		
		return new GenericResponse();
		
	}
	
}