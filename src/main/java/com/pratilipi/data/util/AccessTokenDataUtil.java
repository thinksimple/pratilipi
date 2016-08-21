package com.pratilipi.data.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AccessToken;

public class AccessTokenDataUtil {
	
	public static final long MIN_EXPIRY_MILLIS = TimeUnit.MILLISECONDS.convert( 15, TimeUnit.DAYS );
	public static final long MAX_EXPIRY_MILLIS = TimeUnit.MILLISECONDS.convert( 30, TimeUnit.DAYS );

	
	public static AccessToken newUserAccessToken() {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		AccessToken accessToken = dataAccessor.newAccessToken();

		accessToken.setUserId( 0L );
		accessToken.setExpiry( new Date( new Date().getTime() + MAX_EXPIRY_MILLIS ) );
		accessToken.setCreationDate( new Date() );
		
		accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
		return accessToken;
	}
	
}
