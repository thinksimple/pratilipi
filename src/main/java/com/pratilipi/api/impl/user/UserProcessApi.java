package com.pratilipi.api.impl.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.PostUserProcessRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
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
		
		// Fetching AppProperty
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date( 0 ) );
		}

		
		Query<UserEntity> query = ObjectifyService.ofy().load().type( UserEntity.class )
				.filter( "SIGN_UP_DATE >", appProperty.getValue() )
				.order( "SIGN_UP_DATE" )
				.limit( 10000 );

		
		QueryResultIterator <Key<UserEntity>> iterator = query.keys().iterator();
		List<Long> userIdList = new ArrayList<Long>();
		while( iterator.hasNext() )
			userIdList.add( iterator.next().getId() );
		
		
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
			User user = dataAccessor.getUser( request.getUserId() );

			
			// DELETED User entity can not have a non-DELETED Author entity linked.
			if( user.getState() == UserState.DELETED ) {
				Author author = dataAccessor.getAuthorByUserId( user.getId() );
				if( author != null && author.getState() != AuthorState.DELETED )
					throw new InvalidArgumentException( "DELETED User entity has a non-DELETED Author entity linked." );
				// TODO: DELETED User entity can not have non-DELETED UserPratilipi entities.
				// TODO: DELETED User entity can not have non-DELETED UserAuthor entities.
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
				Query<UserEntity> query = ObjectifyService.ofy().load().type( UserEntity.class )
						.filter( "EMAIL", user.getEmail() )
						.filter( "STATE !=", UserState.DELETED )
						.order( "STATE" )
						.order( "SIGN_UP_DATE" );
				List<UserEntity> list = query.list();
				if( list.size() != 1 )
					throw new InvalidArgumentException( list.size() + " non-DELETED User entities found for email " + user.getEmail() + " ." );
			}
			
			// Only one non-DELETED User entity can exist per facebook id.
			if( user.getFacebookId() != null ) {
				Query<UserEntity> query = ObjectifyService.ofy().load().type( UserEntity.class )
						.filter( "FACEBOOK_ID", user.getFacebookId() )
						.filter( "STATE !=", UserState.DELETED )
						.order( "STATE" )
						.order( "SIGN_UP_DATE" );
				List<UserEntity> list = query.list();
				if( list.size() != 1 )
					throw new InvalidArgumentException( list.size() + " non-DELETED User entities found for Facebook Id " + user.getFacebookId() + " ." );
			}


			// Author profile for the user.
			Query<AuthorEntity> query = ObjectifyService.ofy().load().type( AuthorEntity.class )
					.filter( "USER_ID", user.getId() )
					.filter( "STATE !=", AuthorState.DELETED )
					.order( "STATE" )
					.order( "REGISTRATION_DATE" );
			List<AuthorEntity> authorList = query.list();
			
			if( authorList.size() == 0 ) {
				
				throw new InvalidArgumentException( "Could not find an Author entity linked." );
				
			} else if( authorList.size() == 1 ) {
				
				// Do Nothing.
				
			} else { // if( authorList.size() > 1 )
				
				throw new InvalidArgumentException( "User has " + authorList.size() + " non-DELETED Author entities linked." );
				
			}

		}

		return new GenericResponse();
		
	}

}