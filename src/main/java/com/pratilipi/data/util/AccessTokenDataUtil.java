package com.pratilipi.data.util;

import java.util.Date;
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.AccessTokenType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AccessToken;

public class AccessTokenDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( AccessTokenDataUtil.class.getName() );
	
	private static final long ONE_DAY_MILLIS = 24 * 60 * 60 * 1000;
	private static final long ONE_MONTH_MILLIS = 30 * ONE_DAY_MILLIS;

	
	public static AccessToken newUserAccessToken() {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		AccessToken accessToken = dataAccessor.newAccessToken();

		accessToken.setUserId( 0L );
		accessToken.setType( AccessTokenType.USER );
		accessToken.setExpiry( new Date( ONE_MONTH_MILLIS ) );
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
		} else if( accessToken.getType() != AccessTokenType.USER ) {
			errorMessages.addProperty( "accessToken", "Access Token type is invalid !" );
			throw new InvalidArgumentException( errorMessages );
		} else if( accessToken.isExpired() ) {
			errorMessages.addProperty( "accessToken", "Access Token is expired !" );
			throw new InvalidArgumentException( errorMessages );
		} else if( accessToken.getExpiry().after( new Date( ONE_DAY_MILLIS - ONE_DAY_MILLIS ) ) ) {
			return accessToken;
		} else {
			AccessToken newAccessToken = dataAccessor.newAccessToken();

			newAccessToken.setParentId( accessTokenId );
			newAccessToken.setUserId( accessToken.getUserId() );
			newAccessToken.setType( accessToken.getType() );
			newAccessToken.setExpiry( new Date( ONE_MONTH_MILLIS ) );
			newAccessToken.setCreationDate( new Date() );
			
			newAccessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
			return newAccessToken;
		}
		
	}
	
}
