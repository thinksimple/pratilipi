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
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.GaeQueryBuilder;
import com.pratilipi.data.GaeQueryBuilder.Operator;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

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
		_backfillAuthorLanguage();
		_validateAndUpdateUserProfile();
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

	private void _backfillAuthorLanguage() {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		PersistenceManager pm = dataAccessor.getPersistenceManager();
		
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) );
		gaeQueryBuilder.addFilter( "language", null, Operator.IS_NULL );
		gaeQueryBuilder.setRange( 0, 25 );
		Query query = gaeQueryBuilder.build();
		
		List<Author> authorList = (List<Author>) query.execute();
		
		int count = 0;
		for( Author author : authorList ) {
			if( author.getLanguage() != null ) {
				dataAccessor.createOrUpdateAuthor( author );
				count++;
			}
		}

		logger.log( Level.WARNING, "Backfilled languge for " + count + " Authors." );

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

	private void _validateAndUpdateUserProfile() {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		PersistenceManager pm = dataAccessor.getPersistenceManager();
		
		AppProperty appProperty = dataAccessor.getAppProperty( "Jarvis.Validate.User" );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( "Jarvis.Validate.User" );
			appProperty.setValue( new Date( 0 ) );
		}

		// Fetch next 100 users
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( UserEntity.class ) );
		gaeQueryBuilder.addFilter( "signUpDate", appProperty.getValue(), Operator.GREATER_THAN );
		gaeQueryBuilder.addOrdering( "signUpDate", true );
		gaeQueryBuilder.setRange( 0, 100 );
		Query query = gaeQueryBuilder.build();
		
		List<User> userList = (List<User>) query.execute( appProperty.getValue() );
		
		int count = 0;
		for( User user : userList ) {
			
			// Either email or facebook id should be present.
			if( user.getEmail() == null && user.getFacebookId() == null ) {
				logger.log( Level.SEVERE, "User " + user.getId() + " doesn't have email and facebook id." );
				count++;
				continue;
			}

			// Email, if present, must not be empty.
			if( user.getEmail() != null && user.getEmail().trim().isEmpty() ) {
				logger.log( Level.SEVERE, "User " + user.getId() + " has empty email id." );
				count++;
				continue;
			}
			
			// Facebook id, if present, must not be empty.
			if( user.getFacebookId() != null && user.getFacebookId().trim().isEmpty() ) {
				logger.log( Level.SEVERE, "User " + user.getId() + " has empty facebook id." );
				count++;
				continue;
			}

			// Email id must be in lower case.
			if( ! user.getEmail().equals( user.getEmail().trim().toLowerCase() ) ) {
				logger.log( Level.SEVERE, "User " + user.getEmail() + " doesn't have email in lower case." );
				count++;
				continue;
			}

			// Only one user account can exist per email id.
			if( user.getEmail() != null ) {
				gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( UserEntity.class ) );
				gaeQueryBuilder.addFilter( "email", user.getEmail() );
				gaeQueryBuilder.addFilter( "state", UserState.DELETED, Operator.NOT_EQUALS );
				gaeQueryBuilder.addOrdering( "state", true );
				gaeQueryBuilder.addOrdering( "signUpDate", true );
				query = gaeQueryBuilder.build();
				List<User> list = (List<User>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
				if( list.size() != 1 ) {
					logger.log( Level.SEVERE, "User " + user.getEmail() + " has " + list.size() + " accounts." );
					count++;
					continue;
				}
			}
			
			// Only one user account can exist per facebook id.
			if( user.getFacebookId() != null ) {
				gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( UserEntity.class ) );
				gaeQueryBuilder.addFilter( "facebookId", user.getFacebookId() );
				gaeQueryBuilder.addFilter( "state", UserState.DELETED, Operator.NOT_EQUALS );
				gaeQueryBuilder.addOrdering( "state", true );
				gaeQueryBuilder.addOrdering( "signUpDate", true );
				query = gaeQueryBuilder.build();
				List<User> list = (List<User>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
				if( list.size() != 1 ) {
					logger.log( Level.SEVERE, "User " + user.getId() + " has " + list.size() + " accounts." );
					count++;
					continue;
				}
			}

			
			// Author profile for the user.
			gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) );
			gaeQueryBuilder.addFilter( "userId", user.getId() );
			gaeQueryBuilder.addFilter( "state", AuthorState.DELETED, Operator.NOT_EQUALS );
			gaeQueryBuilder.addOrdering( "state", true );
			gaeQueryBuilder.addOrdering( "registrationDate", true );
			query = gaeQueryBuilder.build();
			List<Author> authorList = (List<Author>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );;
			
			if( authorList.size() == 0 ) {
				gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) );
				gaeQueryBuilder.addFilter( "email", user.getEmail() );
				gaeQueryBuilder.addFilter( "state", AuthorState.DELETED, Operator.NOT_EQUALS );
				gaeQueryBuilder.addOrdering( "state", true );
				gaeQueryBuilder.addOrdering( "registrationDate", true );
				query = gaeQueryBuilder.build();

				List<Author> list = (List<Author>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
				
				if( list.size() == 0 ) {
					if( user.getId().equals( 5069036098420736L ) ) {
						UserData userData = UserDataUtil.createUserData( user );
						userData.setFirstName( user.getFirstName() );
						userData.setLastName( user.getLastName() );
						userData.setGender( user.getGender() );
						Long authorId = AuthorDataUtil.createAuthorProfile( UserDataUtil.createUserData( user ), null );
						Task task = TaskQueueFactory.newTask()
								.setUrl( "/author/process" )
								.addParam( "authorId", authorId.toString() )
								.addParam( "processData", "true" );
						TaskQueueFactory.getAuthorTaskQueue().add( task );
						logger.log( Level.SEVERE, "Created author profile for user " + user.getId() + "." );
					} else {
						logger.log( Level.SEVERE, "User " + user.getId() + " doesn't have a author profile" );
						count++;
					}
					continue;
				}
				
				if( list.size() == 1 ) {
					Author author = list.get( 0 );
					if( author.getUserId() != null )
						logger.log( Level.SEVERE, "User " + user.getId() + "'s author profile is linked with some other user." );
					else
						logger.log( Level.SEVERE, "User " + user.getId() + "'s author profile is not linked." );
					count++;
					continue;
				}
				
				if( list.size() > 1 ) {
					logger.log( Level.SEVERE, "User " + user.getId() + " has " + list.size() + " unlinked author profiles." );
					count++;
					continue;
				}
				
			}
			
			if( authorList.size() == 1 ) {
				Author author = authorList.get( 0 );
				if( ( user.getEmail() == null && author.getEmail() != null ) || ( user.getEmail() != null && ! user.getEmail().equals( author.getEmail() ) ) ) {
					logger.log( Level.SEVERE, "User " + user.getId() + " email doesn't match with the same in author profile " + author.getEmail() );
					count++;
					continue;
				} else if( dataAccessor.getPage( PageType.AUTHOR, author.getId() ) == null ){
					logger.log( Level.SEVERE, "Author Page missing for user " + user.getId() + "." );
					count++;
					continue;
				}
			}
			
			if( authorList.size() > 1 ) {
				logger.log( Level.SEVERE, "User " + user.getId() + " has " + authorList.size() + " author profiles." );
				count++;
				continue;
			}
	
		}
		
		logger.log( Level.SEVERE, "Found " + count + " issues in " + userList.size() + " user records processed." );
		
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