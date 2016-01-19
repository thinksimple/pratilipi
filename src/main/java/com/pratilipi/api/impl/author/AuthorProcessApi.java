package com.pratilipi.api.impl.author;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.author.shared.AuthorProcessPostRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.GaeQueryBuilder;
import com.pratilipi.data.GaeQueryBuilder.Operator;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.AuthorEntity;
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
		
		_createUpdateStateTasks();
		
		_createValidateDataTasks();
		
		return new GenericResponse();
		
	}
	
	@Post
	public GenericResponse postAuthorProcess( AuthorProcessPostRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		if( request.validateData() )
			_validateAuthorData( request.getAuthorId() );
		
		if( request.processData() ) {
			boolean changed = AuthorDataUtil.createOrUpdateAuthorPageUrl( request.getAuthorId() );
			if( changed ) {
//				AuthorDataUtil.createOrUpdateAuthorDashboardPageUrl( request.getAuthorId() );
				// TODO: Update all Pratilipi's PageUrl ( where AUTHOR_ID == request.getAuthorId() )
			}
			AuthorDataUtil.updateAuthorSearchIndex( request.getAuthorId() );
			PratilipiDataUtil.updatePratilipiSearchIndex( null, request.getAuthorId() );
		}
		
		if( request.processImage() ) { }

		if( request.updateStats() ) {
			boolean changed = AuthorDataUtil.updateAuthorStats( request.getAuthorId() );
			
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			Author author = dataAccessor.getAuthor( request.getAuthorId() );
			if( changed )
				author.setLastProcessDate( new Date() );
			author.setNextProcessDate( new Date( new Date().getTime() + 3600000L ) ); // Now + 1 Hr
			author = dataAccessor.createOrUpdateAuthor( author );
			
			if( changed )
				AuthorDataUtil.updateAuthorSearchIndex( request.getAuthorId() );
		}
		
		return new GenericResponse();
	}

	private void _createUpdateStateTasks() {
		
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
		
	}
	
	private void _createValidateDataTasks() {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Fetching AppProperty
		String appPropertyId = "Api.AuthorProcess.ValidateData";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date( 0 ) );
		}

		// Fetching list of author ids.
		AuthorFilter authorFilter = new AuthorFilter();
		authorFilter.setMinLastUpdate( (Date) appProperty.getValue(), false );
		List<Long> authorIdList = dataAccessor.getAuthorIdList( authorFilter, null, 1000 ).getDataList();

		// Creating one task per author id.
		List<Task> taskList = new ArrayList<>( authorIdList.size() );
		for( Long authorId : authorIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", authorId.toString() )
					.addParam( "validateData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getAuthorTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks." );

		// Updating AppProperty.
		if( authorIdList.size() > 0 ) {
			appProperty.setValue( dataAccessor.getAuthor( authorIdList.get( authorIdList.size() - 1 ) ).getLastUpdated() );
			appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		}
		
	}
	
	private void _validateAuthorData( Long authorId ) throws InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		PersistenceManager pm = dataAccessor.getPersistenceManager();
		Author author = dataAccessor.getAuthor( authorId );

		if( author.getState() == AuthorState.DELETED )
			return;

		// At least one of four name fields must be set.
		if( author.getFirstName() == null && author.getLastName() == null && author.getFirstNameEn() == null && author.getLastNameEn() == null )
			throw new InvalidArgumentException( "Author name is missing." );

		// Email, if present, must be trimmed and converted to lower case.
		if( author.getEmail() != null && ! author.getEmail().equals( author.getEmail().trim().toLowerCase() ) )
			throw new InvalidArgumentException( "Email is either not trimmed or not converted to lower case." );

		// Email must be same as email in User entity.
		if( author.getUserId() != null ) {
			User user = dataAccessor.getUser( author.getUserId() );
			if( ( author.getEmail() == null && user.getEmail() != null ) || ( author.getEmail() != null && ! author.getEmail().equals( user.getEmail() ) ) )
				throw new InvalidArgumentException( "Author email doesn't match with the email in User entity." );
		}
		
		// Only one non-DELETED author entity can exist per email id.
		if( author.getEmail() != null ) {
			GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) );
			gaeQueryBuilder.addFilter( "email", author.getEmail() );
			gaeQueryBuilder.addFilter( "state", AuthorState.DELETED, Operator.NOT_EQUALS );
			gaeQueryBuilder.addOrdering( "state", true );
			gaeQueryBuilder.addOrdering( "registrationDate", true );
			Query query = gaeQueryBuilder.build();
			List<Author> authorList = (List<Author>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
			if( authorList.size() > 1 )
				throw new InvalidArgumentException( authorList.size() + " Author entities found for email " + author.getEmail() + " ." );
		}
		
	}
	
}