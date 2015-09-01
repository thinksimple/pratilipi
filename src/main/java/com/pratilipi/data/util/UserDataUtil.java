package com.pratilipi.data.util;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.pratilipi.api.user.shared.PutUserFacebookLoginRequest;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.util.PasswordUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.User;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

public class UserDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( UserDataUtil.class.getName() );
	
	private static final long ACCESS_TOKEN_EXPIRY = 7 * 24 * 60 * 60 * 1000; // 1 Wk

	
	public static String createUserName( User user ) {
		if( user.getFirstName() != null && user.getLastName() == null )
			return user.getFirstName();
		else if( user.getFirstName() == null && user.getLastName() != null )
			return user.getLastName();
		else if( user.getFirstName() != null && user.getLastName() != null )
			return user.getFirstName() + " " + user.getLastName();
		else if( user.getEmail() != null )
			return user.getEmail();
		else
			return null;
	}
	
	public static UserData createUserData( User user ) {
		UserData userData = new UserData( user.getId() );
		userData.setFirstName( user.getFirstName() );
		userData.setLastName( user.getLastName() );
		userData.setName( createUserName( user ) );
		userData.setIsGuest( user.getId() == null || user.getId().equals( 0L ) );
		return userData;
	}

	public static UserData getGuestUser() {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.newUser();
		user.setFirstName( "Guest" );
		user.setLastName( "User" );
		return createUserData( user );
	}

	public static UserData getCurrentUser() {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		Long userId = accessToken.getUserId();
		if( userId.equals( 0L ) )
			return getGuestUser();
		else
			return createUserData( DataAccessorFactory.getDataAccessor().getUser( accessToken.getUserId() ) );
	}

	public static UserData registerUser( String firstName, String lastName,
			String email, String password, UserSignUpSource signUpSource )
			throws InvalidArgumentException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		User user = dataAccessor.getUserByEmail( email );
		if( user != null ) {
			switch( user.getState() ) {
				case REFERRAL:
					break;
				case REGISTERED:
				case ACTIVE:
				case BLOCKED:
					JsonObject errorMessages = new JsonObject();
					errorMessages.addProperty( "email", "Email is already registered." );
					throw new InvalidArgumentException( errorMessages );
				default:
					logger.log( Level.SEVERE, "User state " + user.getState() + " is not handeled !"  );
					throw new UnexpectedServerException();
			}
		} else {
			user = dataAccessor.newUser();
			user.setEmail( email );
		}

		user.setFirstName( firstName );
		user.setLastName( lastName );
		user.setPassword( PasswordUtil.getSaltedHash( password ) );
		user.setState( UserState.REGISTERED );
		user.setSignUpDate( new Date() );
		user.setSignUpSource( signUpSource );

		user = dataAccessor.createOrUpdateUser( user );
		
		Task task1 = TaskQueueFactory.newTask()
				.setUrl( "/user/email" )
				.addParam( "userId", user.getId().toString() )
				.addParam( "sendWelcomeMail", "true" );
		Task task2 = TaskQueueFactory.newTask()
				.setUrl( "/user/email" )
				.addParam( "userId", user.getId().toString() )
				.addParam( "sendEmailVerificationMail", "true" );
		Task task3 = TaskQueueFactory.newTask()
				.setUrl( "/user/process" )
				.addParam( "userId", user.getId().toString() )
				.addParam( "createAuthorProfile", "true" );
		TaskQueueFactory.getUserTaskQueue().addAll( task1, task2, task3 );

		// Send Welcome mail to the user.
//		EmailTemplate emailTemplate = new EmailTemplate( "welcome" );
//		emailTemplate.setRecipientName( createUserName( user ) );
//		emailTemplate.setRecipientEmail( email );
//		emailTemplate.setLanguage( UxModeFilter.getUserLanguage().getCode() );
//		EmailUtil.sendMail( emailTemplate );
		
		return createUserData( user );
	}
	
		public static UserData registerFacebookUser( PutUserFacebookLoginRequest putUserFacebookLoginRequest, UserSignUpSource signUpSource )
			throws InvalidArgumentException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		User user = dataAccessor.newUser();
		user.setEmail( putUserFacebookLoginRequest.getEmailId() );
		user.setFirstName( putUserFacebookLoginRequest.getFirstName() );
		user.setLastName( putUserFacebookLoginRequest.getLastName() );
		user.setPassword( null );
		user.setDateOfBirth( putUserFacebookLoginRequest.getBirthdayDate() );
		user.setGender( putUserFacebookLoginRequest.getGender() );
		user.setNickName( putUserFacebookLoginRequest.getFullName() );
		user.setFacebookId( putUserFacebookLoginRequest.getUserId() );
		user.setState( UserState.REGISTERED );
		user.setSignUpDate( new Date() );
		user.setSignUpSource( signUpSource );
		user = dataAccessor.createOrUpdateUser( user );
		
		/*
		// Send Welcome mail to the user only if userEmail is valid.
		if( ! user.getEmail().contains( "@facebook.com" ) ) {
			EmailTemplate emailTemplate = new EmailTemplate( "welcome" );
			emailTemplate.setRecipientName( putUserFacebookLoginRequest.getFullName() );
			emailTemplate.setRecipientEmail( putUserFacebookLoginRequest.getEmailId() );
			emailTemplate.setLanguage( UxModeFilter.getUserLanguage().getCode() );
			EmailUtil.sendMail( emailTemplate );
		}
		*/
		return createUserData( user );
	}
	
	public static UserData loginUser( String email, String password )
			throws InvalidArgumentException {
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( email );

		JsonObject errorMessages = new JsonObject();
		if( user == null ) {
			errorMessages.addProperty( "email", "Email is not registered !" );
			throw new InvalidArgumentException( errorMessages );
		}
		
		if( ! PasswordUtil.check( password, user.getPassword() ) ) {
			errorMessages.addProperty( "password", "Incorrect password !" );
			throw new InvalidArgumentException( errorMessages );
		}

		if( ! accessToken.getUserId().equals( 0L ) )
			return createUserData( user );
		
		accessToken.setUserId( user.getId() );
		accessToken.setLogInDate( new Date() );
		accessToken.setExpiry( new Date( new Date().getTime() + ACCESS_TOKEN_EXPIRY ) );
		accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
		return createUserData( user );

		/*
		while( true ) { 
			try {
				dataAccessor.beginTx();
				accessToken = dataAccessor.getAccessToken( accessToken.getId() );
				if( ! accessToken.getUserId().equals( 0L ) )
					return createUserData( user );
				accessToken.setUserId( user.getId() );
				accessToken.setLogInDate( new Date() );
				accessToken.setExpiry( new Date( new Date().getTime() + ACCESS_TOKEN_EXPIRY ) );
				accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
				dataAccessor.commitTx();8
				return createUserData( user );
			} catch( JDODataStoreException ex ) {
				logger.log( Level.INFO, "Transaction failed. Retrying in 100 ms...", ex );
			} finally {
				if( dataAccessor.isTxActive() )
					dataAccessor.rollbackTx();
			}
			
			try {
				Thread.sleep( 1000 );
			} catch( InterruptedException e ) { } // Do nothing
		}
		*/
	}
	
	public static UserData loginFacebookUser( PutUserFacebookLoginRequest putUserFacebookLoginRequest )
			throws InvalidArgumentException, UnexpectedServerException {
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( putUserFacebookLoginRequest.getEmailId() );

		if( user == null ) {
			logger.log( Level.SEVERE, "Facebook User " + putUserFacebookLoginRequest.getEmailId() + " is not registered !" );
			return new UserData( 0L );
		}
		
			
		
		if( ! accessToken.getUserId().equals( 0L ) )
			return createUserData( user );
		
		accessToken.setUserId( user.getId() );
		accessToken.setLogInDate( new Date() );
		accessToken.setExpiry( new Date( new Date().getTime() + ACCESS_TOKEN_EXPIRY ) );
		accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
		return createUserData( user );

		/*
		while( true ) { 
			try {
				dataAccessor.beginTx();
				accessToken = dataAccessor.getAccessToken( accessToken.getId() );
				if( ! accessToken.getUserId().equals( 0L ) )
					return createUserData( user );
				accessToken.setUserId( user.getId() );
				accessToken.setLogInDate( new Date() );
				accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
				dataAccessor.commitTx();
				return createUserData( user );
			} catch( JDODataStoreException ex ) {
				logger.log( Level.INFO, "Transaction failed. Retrying in 100 ms...", ex );
			} finally {
				if( dataAccessor.isTxActive() )
					dataAccessor.rollbackTx();
			}
			
			try {
				Thread.sleep( 1000 );
			} catch( InterruptedException e ) { } // Do nothing
		}
		*/
	}

	public static UserData updateFacebookUser( PutUserFacebookLoginRequest putUserFacebookLoginRequest ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		// User Doesn't exist.
		if( dataAccessor.getUserByEmail( putUserFacebookLoginRequest.getEmailId() ) == null && dataAccessor.getUserByFacebookId( putUserFacebookLoginRequest.getUserId() ) == null )
			return new UserData( 0L );
		
		
		Boolean isChanged = false;
		User user = dataAccessor.getUserByEmail( putUserFacebookLoginRequest.getEmailId() ) != null ? dataAccessor.getUserByEmail( putUserFacebookLoginRequest.getEmailId() ) : dataAccessor.getUserByFacebookId( putUserFacebookLoginRequest.getUserId() );
		
		if( putUserFacebookLoginRequest.getFirstName() != null ) {
			if( user.getFirstName() == null || user.getFirstName().equals( putUserFacebookLoginRequest.getFirstName() ) == false ) {
				user.setFirstName( putUserFacebookLoginRequest.getFirstName() );
				isChanged = true;
			}
		}
		
		if( putUserFacebookLoginRequest.getLastName() != null ) {
			if( user.getLastName() == null || user.getLastName().equals( putUserFacebookLoginRequest.getLastName() ) == false ) {
				user.setLastName( putUserFacebookLoginRequest.getLastName() );
				isChanged = true;
			}
		}
		
		if( putUserFacebookLoginRequest.getEmailId() != null ) {
			if( user.getEmail() == null || user.getEmail().equals( putUserFacebookLoginRequest.getEmailId() ) == false ) {
				if( ! putUserFacebookLoginRequest.getEmailId().contains( "@facebook.com" ) );
					user.setEmail( putUserFacebookLoginRequest.getEmailId() );
				isChanged = true;
			}
		}
		
		if( putUserFacebookLoginRequest.getBirthdayDate() != null ) {
			if( user.getDateOfBirth() == null || user.getDateOfBirth().equals( putUserFacebookLoginRequest.getBirthdayDate() ) == false ) {
				user.setDateOfBirth( putUserFacebookLoginRequest.getBirthdayDate() );
				isChanged = true;
			}
		}
		
		if( putUserFacebookLoginRequest.getGender() != null ) {
			if( user.getGender() == null || user.getGender().equals( putUserFacebookLoginRequest.getGender() ) == false ) {
				user.setGender( putUserFacebookLoginRequest.getGender() );
				isChanged = true;
			}
		}
		
		if( putUserFacebookLoginRequest.getFullName() != null ) {
			if( user.getNickName() == null || user.getNickName().equals( putUserFacebookLoginRequest.getFullName() ) == false ) {
				user.setNickName( putUserFacebookLoginRequest.getFullName() );
				isChanged = true;
			}
		}
		
		if( isChanged ) 
			user = dataAccessor.createOrUpdateUser( user );
		
		return createUserData( user );
	}
	
	public static UserData logoutUser() {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		AccessToken accessToken = AccessTokenFilter.getAccessToken();

		if( accessToken.getUserId().equals( 0L ) )
			return getGuestUser();

		accessToken.setLogOutDate( new Date() );
		accessToken.setExpiry( accessToken.getLogOutDate() );
		accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
		return getGuestUser();

		/*
		while( true ) {
			try {
				dataAccessor.beginTx();
				accessToken = dataAccessor.getAccessToken( accessToken.getId() );
				if( accessToken.getUserId().equals( 0L ) )
					return getGuestUser();
				accessToken.setLogOutDate( new Date() );
				accessToken.setExpiry( new Date() );
				accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
				dataAccessor.commitTx();
				return getGuestUser();
			} catch( JDODataStoreException ex ) {
				logger.log( Level.INFO, "Transaction failed. Retrying in 100 ms...", ex );
			} finally {
				if( dataAccessor.isTxActive() )
					dataAccessor.rollbackTx();
			}
			
			try {
				Thread.sleep( 1000 );
			} catch( InterruptedException e ) { } // Do nothing
		}
 		*/
	}
	
}
