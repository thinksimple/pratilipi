package com.pratilipi.data.util;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.common.util.PasswordUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.FacebookUserData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.User;
import com.pratilipi.filter.AccessTokenFilter;

public class UserDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( UserDataUtil.class.getName() );
	
	
	public static String createUserName( User user ) {
		if( user.getFirstName() != null && user.getLastName() == null )
			return user.getFirstName();
		else if( user.getFirstName() == null && user.getLastName() != null )
			return user.getLastName();
		else if( user.getFirstName() != null && user.getLastName() != null )
			return user.getFirstName() + " " + user.getLastName();
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

		return createUserData( user );
	}
	
	public static UserData loginUser( String email, String password )
			throws InvalidArgumentException, UnexpectedServerException {
		
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

		loginUser( AccessTokenFilter.getAccessToken(), user );
		return createUserData( user );

	}
	
	public static Boolean isNewUser( String fbUserId, String fbAccessToken ) throws UnexpectedServerException {
		// Returns true if user is new.
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		String fbUserEmail = FacebookApi.getFbUserEmail( fbAccessToken );
		if( fbUserEmail != null )
			return dataAccessor.getUserByFacebookId( fbUserId ) == null && 
				dataAccessor.getUserByEmail( fbUserEmail ) == null;
		else
			return dataAccessor.getUserByFacebookId( fbUserId ) == null;
	}

	public static UserData loginUser( String fbUserId, String fbUserAccessToken,
			UserSignUpSource signUpSource )
			throws InvalidArgumentException, UnexpectedServerException {
		
		FacebookUserData fbUserData = FacebookApi.getUserCredentials( fbUserId, fbUserAccessToken );

		if( fbUserData == null )
			throw new InvalidArgumentException( "Invalid Facebook UserId or UserAccessToken." );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		User user = dataAccessor.getUserByFacebookId( fbUserId );
		Boolean isChanged = false;

		if( user != null ) {

			if( fbUserData.getEmail() != null && ! fbUserData.getEmail().equals( user.getEmail() ) && user.getPassword() == null ) {
				user.setEmail( fbUserData.getEmail() );
				isChanged = true;
			}
			
		} else { // user == null
			
			if( fbUserData.getEmail() != null )
				user = dataAccessor.getUserByEmail( fbUserData.getEmail() );
			
			if( user == null ) {
				user = dataAccessor.newUser();
				user.setEmail( fbUserData.getEmail() );
				if( fbUserData.getVerified() )
					user.setState( UserState.ACTIVE );
				else
					user.setState( UserState.REGISTERED );
				user.setSignUpDate( new Date() );
				user.setSignUpSource( signUpSource );
			}
			
			user.setFacebookId( fbUserId );
			isChanged = true;
		
		}
		
		
		if( fbUserData.getFirstName() != null && ! fbUserData.getFirstName().equals( user.getFirstName() ) ) {
			user.setFirstName( fbUserData.getFirstName() );
			isChanged = true;
		}
		
		if( fbUserData.getLastName() != null && ! fbUserData.getLastName().equals( user.getLastName() ) ) {
			user.setLastName( fbUserData.getLastName() );
			isChanged = true;
		}
		
		if( fbUserData.getGender() != null && fbUserData.getGender() != user.getGender() ) {
			user.setGender( fbUserData.getGender() );
			isChanged = true;
		}
		
		if( fbUserData.getDateOfBirth() != null && ! fbUserData.getDateOfBirth().equals( user.getDateOfBirth() ) ) {
			user.setDateOfBirth( fbUserData.getDateOfBirth() );
			isChanged = true;
		}

		if( isChanged )
			user = dataAccessor.createOrUpdateUser( user );
		

		loginUser( AccessTokenFilter.getAccessToken(), user );
		return createUserData( user );
	}
	
	private static void loginUser( AccessToken accessToken, User user ) {
		
		if( ! accessToken.getUserId().equals( 0L ) )
			return;
		
		accessToken.setUserId( user.getId() );
		accessToken.setLogInDate( new Date() );
		accessToken = DataAccessorFactory.getDataAccessor().createOrUpdateAccessToken( accessToken );

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