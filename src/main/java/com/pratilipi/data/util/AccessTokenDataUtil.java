package com.pratilipi.data.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonObject;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AccessToken;

public class AccessTokenDataUtil {
	
	private static final long ONE_MONTH_MILLIS = TimeUnit.MILLISECONDS.convert( 30, TimeUnit.DAYS );

	
	public static AccessToken newUserAccessToken() {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		AccessToken accessToken = dataAccessor.newAccessToken();

		accessToken.setUserId( 0L );
		accessToken.setExpiry( new Date( new Date().getTime() + ONE_MONTH_MILLIS ) );
		accessToken.setCreationDate( new Date() );
		
		accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
		return accessToken;
	}
	
	public static AccessToken renewUserAccessToken( String accessTokenId )
			throws InvalidArgumentException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		AccessToken accessToken = dataAccessor.getAccessToken( accessTokenId );
		JsonObject errorMessages = new JsonObject();

		if( accessToken == null ) {
			
			errorMessages.addProperty( "accessToken", "Access Token is invalid !" );
			throw new InvalidArgumentException( errorMessages );
			
		} else if( accessToken.isExpired() ) {
			
			AccessToken newAccessToken = dataAccessor.newAccessToken();

			newAccessToken.setParentId( accessTokenId );
			newAccessToken.setExpiry( new Date( new Date().getTime() + ONE_MONTH_MILLIS ) );
			newAccessToken.setCreationDate( new Date() );
			
			newAccessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
			return newAccessToken;
			
		} else if( accessToken.getExpiry().getTime() > new Date().getTime() + ONE_MONTH_MILLIS / 2 ) {
			
			return accessToken;
			
		} else {
			
			AccessToken newAccessToken = dataAccessor.newAccessToken();

			newAccessToken.setParentId( accessTokenId );
			newAccessToken.setUserId( accessToken.getUserId() );
			newAccessToken.setExpiry( new Date( new Date().getTime() + ONE_MONTH_MILLIS ) );
			newAccessToken.setCreationDate( new Date() );
			
			newAccessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
			return newAccessToken;
			
		}
		
	}
	
}
