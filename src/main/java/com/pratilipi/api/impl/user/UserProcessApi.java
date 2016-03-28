package com.pratilipi.api.impl.user;

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
import com.pratilipi.api.impl.user.shared.PostUserProcessRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.GaeQueryBuilder;
import com.pratilipi.data.GaeQueryBuilder.Operator;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/user/process" )
public class UserProcessApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( UserProcessApi.class.getName() );
	
	private static final String appPropertyId = "Api.UserProcess.ValidateData";
	
	
	@Get
	public GenericResponse get( GenericRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		PersistenceManager pm = dataAccessor.getPersistenceManager();
		
		// Fetching AppProperty
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date( 0 ) );
		}

		// Fetching list of user ids.
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( UserEntity.class ) );
		gaeQueryBuilder.addFilter( "signUpDate", appProperty.getValue(), Operator.GREATER_THAN );
		gaeQueryBuilder.addOrdering( "signUpDate", true );
		gaeQueryBuilder.setResult( "id" );
		gaeQueryBuilder.setRange( 0, 10000 );
		Query query = gaeQueryBuilder.build();
		List<Long> userIdList = (List<Long>) query.execute( appProperty.getValue() );
		
		// Creating task for each user.
		List<Task> taskList = new ArrayList<>( userIdList.size() );
		for( Long userId : userIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/user/process" )
					.addParam( "userId", userId.toString() )
					.addParam( "validateData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getUserOfflineTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks." );
		
		// Updating AppProperty.
		if( userIdList.size() > 0 ) {
			appProperty.setValue( dataAccessor.getUser( userIdList.get( userIdList.size() - 1 ) ).getSignUpDate() );
			dataAccessor.createOrUpdateAppProperty( appProperty );
		}
		
		return new GenericResponse();
		
	}
	
	@Post
	public GenericResponse post( PostUserProcessRequest request )
			throws InvalidArgumentException {

		if( request.validateData() ) {
			
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			PersistenceManager pm = dataAccessor.getPersistenceManager();
			User user = dataAccessor.getUser( request.getUserId() );

			
			// DELETED User entity can not have a non-DELETED Author entity linked.
			if( user.getState() == UserState.DELETED ) {
				Author author = dataAccessor.getAuthorByUserId( user.getId() );
				if( author != null && author.getState() != AuthorState.DELETED )
					throw new InvalidArgumentException( "DELETED User entity has a non-DELETED Author entity linked." );
				// TODO: DELETED User entity can not have non-DELETED UserPratilipi entities.
				return new GenericResponse();
			}

			
			// Either of two - email and facebook id - must be present.
			if( user.getEmail() == null && user.getFacebookId() == null )
				throw new InvalidArgumentException( "Neither email nor facebook id is set." );

			// Email, if present, must not be empty.
			if( user.getEmail() != null && user.getEmail().trim().isEmpty() )
				throw new InvalidArgumentException( "Email is empty." );
			
			// Facebook id, if present, must not be empty.
			if( user.getFacebookId() != null && user.getFacebookId().trim().isEmpty() )
				throw new InvalidArgumentException( "Facebook Id is empty." );

			// Email, if present, must be trimmed and converted to lower case.
			if( user.getEmail() != null && ! user.getEmail().equals( user.getEmail().trim().toLowerCase() ) )
				throw new InvalidArgumentException( "Email is either not trimmed or not converted to lower case." );

			// Only one non-DELETED User entity can exist per email id.
			if( user.getEmail() != null ) {
				GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( UserEntity.class ) );
				gaeQueryBuilder.addFilter( "email", user.getEmail() );
				gaeQueryBuilder.addFilter( "state", UserState.DELETED, Operator.NOT_EQUALS );
				gaeQueryBuilder.addOrdering( "state", true );
				gaeQueryBuilder.addOrdering( "signUpDate", true );
				Query query = gaeQueryBuilder.build();
				List<User> list = (List<User>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
				if( list.size() != 1 )
					throw new InvalidArgumentException( list.size() + " non-DELETED User entities found for email " + user.getEmail() + " ." );
			}
			
			// Only one non-DELETED User entity can exist per facebook id.
			if( user.getFacebookId() != null ) {
				GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( UserEntity.class ) );
				gaeQueryBuilder.addFilter( "facebookId", user.getFacebookId() );
				gaeQueryBuilder.addFilter( "state", UserState.DELETED, Operator.NOT_EQUALS );
				gaeQueryBuilder.addOrdering( "state", true );
				gaeQueryBuilder.addOrdering( "signUpDate", true );
				Query query = gaeQueryBuilder.build();
				List<User> list = (List<User>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
				if( list.size() != 1 )
					throw new InvalidArgumentException( list.size() + " non-DELETED User entities found for Facebook Id " + user.getFacebookId() + " ." );
			}

			// REFERRAL User entity can not have a non-DELETED Author entity linked.
			if( user.getState() == UserState.REFERRAL ) {
				Author author = dataAccessor.getAuthorByUserId( user.getId() );
				if( author != null && author.getState() != AuthorState.DELETED )
					throw new InvalidArgumentException( "REFERRAL User entity has a non-DELETED Author entity linked." );
				return new GenericResponse();
			}


			// Author profile for the user.
			GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) );
			gaeQueryBuilder.addFilter( "userId", user.getId() );
			gaeQueryBuilder.addFilter( "state", AuthorState.DELETED, Operator.NOT_EQUALS );
			gaeQueryBuilder.addOrdering( "state", true );
			gaeQueryBuilder.addOrdering( "registrationDate", true );
			Query query = gaeQueryBuilder.build();
			List<Author> authorList = (List<Author>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );;
			
			if( authorList.size() == 0 ) {
				
				List<Author> authorList2 = null;
				
				// Try to find Author profile by his email
				if( user.getEmail() != null ) {
					gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) );
					gaeQueryBuilder.addFilter( "email", user.getEmail() );
					gaeQueryBuilder.addFilter( "state", AuthorState.DELETED, Operator.NOT_EQUALS );
					gaeQueryBuilder.addOrdering( "state", true );
					gaeQueryBuilder.addOrdering( "registrationDate", true );
					query = gaeQueryBuilder.build();
					authorList2 = (List<Author>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
				}
				
				if( authorList2 == null || authorList2.size() == 0 ) {
					
/*					logger.log( Level.SEVERE, "Could not find an Author entity for this User. Creating a new one ..." );
					UserData userData = UserDataUtil.createUserData( user );
					userData.setFirstName( user.getFirstName() );
					userData.setLastName( user.getLastName() );
					userData.setGender( user.getGender() );
					AuthorDataUtil.createAuthorProfile( userData, null );*/
					
					throw new InvalidArgumentException( "Could not find an Author entity linked." );
					
				} else if( authorList2.size() == 1 ) {
					
					Author author = authorList2.get( 0 );
					if( author.getUserId() == null ) {
/*						logger.log( Level.SEVERE, "Author entity is not linked for the User. Linking it ..." );
						author.setUserId( user.getId() );
						dataAccessor.createOrUpdateAuthor( author );*/
						throw new InvalidArgumentException( "Author entity is not linked." );
					} else {
						throw new InvalidArgumentException( "Author entity, having email same as User's email, is linked to a different User entity." );
					}
				
				} else { // if( userList.size() > 1 )
					
					throw new InvalidArgumentException( authorList.size() + " Author entities, having email same as User's email, found." );
					
				}
				
			} else if( authorList.size() == 1 ) {
				
				Author author = authorList.get( 0 );
				if( ( user.getEmail() == null && author.getEmail() != null ) || ( user.getEmail() != null && ! user.getEmail().equals( author.getEmail() ) ) ) {
					throw new InvalidArgumentException( "Email in User entity doesn't match with the email in linked Author entity." );
				} else if( dataAccessor.getPage( PageType.AUTHOR, author.getId() ) == null ) {
					throw new InvalidArgumentException( "Page entity is missing for the User/Author." );
				} else {
					// Do Nothing.
				}
				
			} else { // if( authorList.size() > 1 )
				
				throw new InvalidArgumentException( "User has " + authorList.size() + " Author entities linked." );
				
			}

		}

		return new GenericResponse();
		
	}

}