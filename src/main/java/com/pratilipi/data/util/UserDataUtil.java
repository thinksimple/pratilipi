package com.pratilipi.data.util;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDODataStoreException;

import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.util.PasswordUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.User;
import com.pratilipi.site.AccessTokenFilter;

public class UserDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( UserDataUtil.class.getName() );
	
	
	private static final long ACCESS_TOKEN_EXPIRY = 7 * 24 * 60 * 60 * 1000; // 1 Wk

	
	public static UserData createUserData( User user ) {
		UserData userData = new UserData();

		userData.setFirstName( user.getFirstName() );
		userData.setLastName( user.getLastName() );
		
		if( user.getFirstName() != null || user.getLastName() == null )
			userData.setName( user.getFirstName() );
		else if( user.getFirstName() == null || user.getLastName() != null )
			userData.setName( user.getLastName() );
		else if( user.getFirstName() != null || user.getLastName() != null )
			userData.setName( user.getFirstName() + " " + user.getLastName() );

		userData.setIsGuest( user.getId().equals( 0L ) );
		
		return userData;
	}

	
	public static UserData getCurrentUser() {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		Long userId = accessToken.getUserId();
		if( userId.equals( 0L ) ) {
			User user = dataAccessor.newUser();
			user.setId( 0L );
			user.setFirstName( "Guest" );
			user.setLastName( "User" );
			return createUserData( user );
		} else {
			User user = dataAccessor.getUser( accessToken.getUserId() );
			return createUserData( user );
		}
	}
	
	public static UserData loginUser( String email, String password )
			throws InvalidArgumentException {
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( email );

		if( user == null )
			throw new InvalidArgumentException( "Invalid user id." );
		
		if( ! PasswordUtil.check( password, user.getPassword() ) )
			throw new InvalidArgumentException( "Wrong password !" );

		while( true ) {
			try {
				dataAccessor.beginTx();
				accessToken = dataAccessor.getAccessToken( accessToken.getId() );
				if( ! accessToken.getUserId().equals( 0L ) )
					throw new InvalidArgumentException( "Invalid token !" );
				accessToken.setUserId( user.getId() );
				accessToken.setExpiry( new Date( new Date().getTime() + ACCESS_TOKEN_EXPIRY ) );
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
			} catch( InterruptedException e ) {
				// Do nothing
			}
		}
		
	}
	
}
