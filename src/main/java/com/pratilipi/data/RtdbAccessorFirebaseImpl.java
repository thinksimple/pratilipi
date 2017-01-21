package com.pratilipi.data;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.HttpUtil;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.UserPreferenceRtdb;
import com.pratilipi.data.type.rtdb.UserPreferenceRtdbImpl;

public class RtdbAccessorFirebaseImpl implements RtdbAccessor {

	private static final Logger logger =
			Logger.getLogger( RtdbAccessorFirebaseImpl.class.getName() );
	
	private static final String DATABASE_URL = "https://prod-pratilipi.firebaseio.com/";
	private static final String DATABASE_PREFERENCE_TABLE = "PREFERENCE/";

	
	private final Map<String, String> headersMap;
	private final Memcache memcache;
	
	
	public RtdbAccessorFirebaseImpl( String googleApiAccessToken, Memcache memcache ) {
		this.headersMap = new HashMap<>();
		this.headersMap.put( "Authorization", "Bearer " + googleApiAccessToken );
		this.memcache = memcache;
	}

	
	// PREFERENCE Table
	
	@Override
	public UserPreferenceRtdb getUserPreference( Long userId ) throws UnexpectedServerException {
		String memcacheId = "Firebase.PREFERENCE." + userId;
		String json = memcache.get( memcacheId );
		if( json == null ) {
			try {
				BlobEntry blobEntry = HttpUtil.doGet( DATABASE_URL + DATABASE_PREFERENCE_TABLE + userId + ".json", headersMap, null );
				json = new String( blobEntry.getData(), "UTF-8" );
				if( json.equals( "null" ) )
					json = "{}";
				memcache.put( memcacheId, json, 5 );
			} catch( UnsupportedEncodingException | JsonSyntaxException e ) {
				logger.log( Level.SEVERE, e.getMessage() );
				throw new UnexpectedServerException();
			}
		}
		return new Gson().fromJson( json, UserPreferenceRtdbImpl.class );
	}

}
