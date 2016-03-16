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
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.GaeQueryBuilder;
import com.pratilipi.data.GaeQueryBuilder.Operator;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
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
			
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			Author author = dataAccessor.getAuthor( request.getAuthorId() );
			
			// If Author email is set to null, set its value back from linked User entity.
			if( author.getUserId() != null && author.getEmail() == null ) {
				User user = dataAccessor.getUser( author.getUserId() );
				if( user.getEmail() != null ) {
					author.setEmail( user.getEmail() );
					author = dataAccessor.createOrUpdateAuthor( author );
				}
			// If no User entity is linked and a User entity exist with the same email id, unset the email in Author entity.
			} else if( author.getUserId() == null && author.getEmail() != null ) {
				User user = dataAccessor.getUserByEmail( author.getEmail() );
				if( user != null ) {
					author.setEmail( null );
					author = dataAccessor.createOrUpdateAuthor( author );
				}
			// If Author email is same as some other user's email, revert it else update it to linked User entity.
			} else if( author.getUserId() != null && author.getEmail() != null ) {
				User user = dataAccessor.getUser( author.getUserId() );
				User user2 = dataAccessor.getUserByEmail( author.getEmail() );
				// Update email in User entity.
				if( user2 == null ) {
					user.setEmail( author.getEmail() );
					if( user.getState() == UserState.ACTIVE )
						user.setState( UserState.REGISTERED );
					user = dataAccessor.createOrUpdateUser( user );
				// Revert email
				} else if( user2 != null && ! user2.getId().equals( author.getUserId() ) ) {
					author.setEmail( user.getEmail() );
					author = dataAccessor.createOrUpdateAuthor( author );
				}
			}
			
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
		PersistenceManager pm = dataAccessor.getPersistenceManager();
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