package com.pratilipi.api.impl.init;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetInitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.GaeQueryBuilder;
import com.pratilipi.data.GaeQueryBuilder.Operator;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) {
		
/*		
		List<Long> authorIdList = DataAccessorFactory.getDataAccessor()
				.getAuthorIdList( new AuthorFilter(), null, null )
				.getDataList();

		List<Task> taskList = new ArrayList<Task>( authorIdList.size() );
		for( Long authorId : authorIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", authorId.toString() )
					.addParam( "processData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getAuthorTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks in the queue." );
*/
		
/*		
		List<Long> pratilipiIdList = DataAccessorFactory.getDataAccessor()
				.getPratilipiIdList( new PratilipiFilter(), null, null )
				.getDataList();

		List<Task> taskList = new ArrayList<Task>( pratilipiIdList.size() );
		for( Long pratilipiId : pratilipiIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiId", pratilipiId.toString() )
					.addParam( "processData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks in the queue." );
*/
		
/*		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		String cursor = null;
		int count = 0;
		do {
			DataListCursorTuple<Author> authorListCursorTuple =
					dataAccessor.getAuthorList( new AuthorFilter(), cursor, 100 );
			List<Author> authorList = authorListCursorTuple.getDataList();
			cursor = authorListCursorTuple.getCursor();
			count = count + authorList.size();
			for( Author author : authorList ) {
				if( author.getUserId() != null ) {
					User user = dataAccessor.getUser( author.getUserId() );
					if( ! author.getEmail().equals( user.getEmail() ) )
						logger.log( Level.SEVERE, "Author email " + author.getEmail() + " doesn't match with user email " + user.getEmail() );
				}
			}
		} while( cursor != null );
		
		logger.log( Level.INFO, "Checked " + count + " author records." );
*/
		
		_backfillUserStateAndSignUpSource();
		_backfillPratilipiLanguage();
		
		_validateAndUpdateAuthorProfile();
		
		_backfillUserReviewState();
		_publishUserReview();
		
		return new GenericResponse();
		
	}
	
	private void _backfillPratilipiLanguage() {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		PersistenceManager pm = dataAccessor.getPersistenceManager();
		
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( PratilipiEntity.class ) );
		gaeQueryBuilder.addFilter( "language", null, Operator.IS_NULL );
		gaeQueryBuilder.setRange( 0, 25 );
		Query query = gaeQueryBuilder.build();
		
		List<Pratilipi> pratilipiList = (List<Pratilipi>) query.execute();
		
		int count = 0;
		for( Pratilipi pratilipi : pratilipiList ) {
			pratilipi.getLanguage();
			dataAccessor.createOrUpdatePratilipi( pratilipi );
			count++;
		}

		logger.log( Level.WARNING, "Backfilled languge for " + count + " Pratilipis." );

	}

	private void _backfillUserStateAndSignUpSource() {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		PersistenceManager pm = dataAccessor.getPersistenceManager();
		
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( UserEntity.class ) );
		gaeQueryBuilder.addFilter( "state", null, Operator.IS_NULL );
		gaeQueryBuilder.setRange( 0, 25 );
		Query query = gaeQueryBuilder.build();
		
		List<User> userList = (List<User>) query.execute();
		
		int count = 0;
		for( User user : userList ) {
			user.getState();
			user.getSignUpSource();
			dataAccessor.createOrUpdateUser( user );
			count++;
		}
		
		logger.log( Level.WARNING, "Backfilled state & signUpSouce for " + count + " user records." );

	}

	private void _validateAndUpdateAuthorProfile() {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		PersistenceManager pm = dataAccessor.getPersistenceManager();
		
		AppProperty appProperty = dataAccessor.getAppProperty( "Jarvis.Validate.Author" );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( "Jarvis.Validate.Author" );
			appProperty.setValue( new Date( 0 ) );
		}

		// Fetch next 100 users
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) );
		gaeQueryBuilder.addFilter( "lastUpdated", appProperty.getValue(), Operator.GREATER_THAN_OR_EQUAL );
		gaeQueryBuilder.addOrdering( "lastUpdated", true );
		gaeQueryBuilder.setRange( 0, 100 );
		Query query = gaeQueryBuilder.build();
		
		List<Author> authorList = (List<Author>) query.execute( appProperty.getValue() );
		
		int count = 0;
		for( Author author : authorList ) {
			
			if( author.getState() == AuthorState.DELETED ) {
				continue;
			}

			if( author.getEmail() == null ) {
				continue;
			}
			
			if( ! author.getEmail().equals( author.getEmail().trim().toLowerCase() ) ) {
				logger.log( Level.WARNING, author.getEmail() + " email is not trimmed or in lower case " );
				count++;
				continue;
			}
			
			if( author.getUserId() != null ) {
				User user = dataAccessor.getUser( author.getUserId() );
				if( ! author.getEmail().equals( user.getEmail() ) ) {
					logger.log( Level.WARNING, "Author email " + author.getEmail() + " doesn't match with the same in user table " + user.getEmail() );
					count++;
					continue;
				}
			}

			gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) );
			gaeQueryBuilder.addFilter( "email", author.getEmail() );
			gaeQueryBuilder.addFilter( "state", AuthorState.DELETED, Operator.NOT_EQUALS );
			gaeQueryBuilder.addOrdering( "state", true );
			gaeQueryBuilder.addOrdering( "registrationDate", true );
			query = gaeQueryBuilder.build();

			List<Author> list = (List<Author>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
			
			if( list.size() > 1 ) {
				logger.log( Level.WARNING, list.size() + " author accouts found for email " + author.getEmail() );
				count++;
				continue;
			}
			
		}

		logger.log( Level.WARNING, count + " issues found in " + authorList.size() + " author records processed." );
		
		if( count == 0 ) {
			appProperty.setValue( authorList.get( authorList.size() - 1 ).getLastUpdated() );
			appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		}

	}
	
	private void _backfillUserReviewState() {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		PersistenceManager pm = dataAccessor.getPersistenceManager();
		
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) );
		gaeQueryBuilder.addFilter( "reviewDate", null, Operator.NOT_NULL );
		gaeQueryBuilder.addFilter( "reviewState", null, Operator.IS_NULL );
		gaeQueryBuilder.setRange( 0, 25 );
		Query query = gaeQueryBuilder.build();
		
		List<UserPratilipi> userPratilipiList = (List<UserPratilipi>) query.execute();
		
		int count = 0;
		for( UserPratilipi userPratilipi : userPratilipiList ) {
			userPratilipi.getReviewState();
			dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
			count++;
		}
		
		logger.log( Level.WARNING, "Backfilled reviewState for " + count + " user reviews." );

	}

	private void _publishUserReview() {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		PersistenceManager pm = dataAccessor.getPersistenceManager();
		
		
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) );
		gaeQueryBuilder.addFilter( "reviewState", UserReviewState.PENDING_APPROVAL );
		gaeQueryBuilder.setRange( 0, 25 );
		Query query = gaeQueryBuilder.build();
		
		List<UserPratilipi> userPratilipiList = (List<UserPratilipi>) query.execute( UserReviewState.PENDING_APPROVAL );
		
		int count = 0;
		for( UserPratilipi userPratilipi : userPratilipiList ) {
			userPratilipi.setReviewState( UserReviewState.PUBLISHED );
			dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
			count++;
		}

		
		gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) );
		gaeQueryBuilder.addFilter( "reviewState", UserReviewState.SUBMITTED );
		gaeQueryBuilder.setRange( 0, 25 );
		query = gaeQueryBuilder.build();
		
		userPratilipiList = (List<UserPratilipi>) query.execute( UserReviewState.SUBMITTED );
		
		for( UserPratilipi userPratilipi : userPratilipiList ) {
			userPratilipi.setReviewState( UserReviewState.PUBLISHED );
			dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
			count++;
		}

		
		logger.log( Level.WARNING, "Published " + count + " user reviews." );

	}

}