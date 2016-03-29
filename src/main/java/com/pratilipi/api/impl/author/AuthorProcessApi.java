package com.pratilipi.api.impl.author;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.UserCampaign;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.User;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/author/process" )
public class AuthorProcessApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( AuthorProcessApi.class.getName() );
	
	
	@Get
	public GenericResponse getAuthorProcess( GenericRequest request ) {
		
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
		List<Long> authorIdList = dataAccessor.getAuthorIdList( authorFilter, null, 10000 ).getDataList();

		// Creating one task per author id.
		List<Task> taskList = new ArrayList<>( authorIdList.size() );
		for( Long authorId : authorIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", authorId.toString() )
					.addParam( "validateData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getAuthorOfflineTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks." );

		// Updating AppProperty.
		if( authorIdList.size() > 0 ) {
			appProperty.setValue( dataAccessor.getAuthor( authorIdList.get( authorIdList.size() - 1 ) ).getLastUpdated() );
			appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		}
		
		return new GenericResponse();
		
	}
	
	@Post
	public GenericResponse postAuthorProcess( AuthorProcessPostRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		if( request.validateData() )
			validateAuthorData( request.getAuthorId() );
		
		if( request.processData() ) {
			
			boolean changed = AuthorDataUtil.createOrUpdateAuthorPageUrl( request.getAuthorId() );
//			AuthorDataUtil.createOrUpdateAuthorDashboardPageUrl( request.getAuthorId() );
			if( changed ) {
				// TODO: Update all Pratilipi's PageUrl ( where AUTHOR_ID == request.getAuthorId() )
			}
			AuthorDataUtil.updateAuthorSearchIndex( request.getAuthorId() );
			PratilipiDataUtil.updatePratilipiSearchIndex( null, request.getAuthorId() );
			
		}
		
		if( request.processImage() ) { }

		if( request.updateStats() ) {
			boolean changed = AuthorDataUtil.updateAuthorStats( request.getAuthorId() );
			if( changed )
				AuthorDataUtil.updateAuthorSearchIndex( request.getAuthorId() );
		}
		
		return new GenericResponse();
	}

	private void validateAuthorData( Long authorId ) throws InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( authorId );
		Page page = dataAccessor.getPage( PageType.AUTHOR, authorId );

		
		// Must have a page entity linked.
		if( page == null )
			throw new InvalidArgumentException( "Page entity is missing." );
		
		// Page entity linked with DELETED Author must not have uriAlias.
		if( author.getState() == AuthorState.DELETED ) {
			if( page.getUriAlias() != null )
				throw new InvalidArgumentException( "Author entity is DELETED but uriAlias in Page entity is not null." );
			// TODO: DELETED Author cannot have non-DELETED Pratilipi entities linked.
			return;
		}

		
		// At least one of four name fields must be set.
		if( author.getFirstName() == null && author.getLastName() == null && author.getFirstNameEn() == null && author.getLastNameEn() == null )
			throw new InvalidArgumentException( "Author name is missing." );

		
		// Author profile, if not for a dead person, must have a linked User Profile.
		if( author.getEmail() != null && author.getUserId() == null ) {
			
			if( ! author.getEmail().endsWith( "@pratilipi.com" ) ) {
				
				User user = dataAccessor.getUserByEmail( author.getEmail() );
				if( user != null )
					throw new InvalidArgumentException( "User with email " + author.getEmail() + " already exists." );
				
				user = dataAccessor.newUser();
				user.setEmail( author.getEmail() );
				user.setState( UserState.REFERRAL );
				user.setCampaign( UserCampaign.AEE_TEAM );
				user.setSignUpDate( author.getRegistrationDate() );
				user.setSignUpSource( UserDataUtil.getUserSignUpSource( false, false ) );
				user.setLastUpdated( new Date() );
				
				user = dataAccessor.createOrUpdateUser( user );

				logger.log( Level.INFO, "Created user for email " + author.getEmail() + " with user id " + user.getId() + " ." );
				
				author.setUserId( user.getId() );
				author = dataAccessor.createOrUpdateAuthor( author );
				
			} else {
				
				logger.log( Level.INFO, "Skipping author with email " + author.getEmail() + " ." );
				
			}
		
		}
		
	}
	
}