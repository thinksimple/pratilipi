package com.pratilipi.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailFrequency;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.common.util.HttpUtil;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.UserPreferenceRtdb;
import com.pratilipi.data.type.rtdb.UserPreferenceRtdbImpl;

public class RtdbAccessorFirebaseImpl implements RtdbAccessor {

	
	private static final Logger logger =
			Logger.getLogger( RtdbAccessorFirebaseImpl.class.getName() );

	
	private static final String DATABASE_URL = "https://prod-pratilipi.firebaseio.com/";
	private static final String DATABASE_PREFERENCE_TABLE = "PREFERENCE";

	
	private final Map<String, String> headersMap;
	
	public RtdbAccessorFirebaseImpl( String googleApiAccessToken ) {
		this.headersMap = new HashMap<>();
		this.headersMap.put( "Authorization", "Bearer " + googleApiAccessToken );
	}


	
	
	private UserPreferenceRtdb _getUserPreferenceRtdb( String json ) {
		return _getUserPreferenceRtdb( new Gson().fromJson( json, JsonElement.class ).getAsJsonObject() );
	}

	private UserPreferenceRtdb _getUserPreferenceRtdb( JsonObject preference ) {

		if( preference.has( "emailFrequency" ) ) {
			String emailFrequency = preference.get( "emailFrequency" ).getAsString();
			if( emailFrequency.equals( "ONCE A DAY" ) ) {
				preference.remove( "emailFrequency" );
				preference.addProperty( "emailFrequency", EmailFrequency.DAILY.name() );
			} else if( emailFrequency.equals( "ONCE A WEEK" ) ) {
				preference.remove( "emailFrequency" );
				preference.addProperty( "emailFrequency", EmailFrequency.WEEKLY.name() );
			}
		}

		if( preference.has( "notificationSubscriptions" ) ) {
			JsonObject notificationSubscriptions = preference.get( "notificationSubscriptions" ).getAsJsonObject();
			List<String> invalidTypes = new ArrayList<>();
			for( Entry<String, JsonElement> type : notificationSubscriptions.entrySet() ) {
				boolean isValid = false;
				for( NotificationType nt : NotificationType.values() ) {
					if( type.getKey().equals( nt.name() ) ) {
						isValid = true;
						break;
					}
				}
				if( ! isValid )
					invalidTypes.add( type.getKey() );
			}
			for( String invalidType : invalidTypes )
				notificationSubscriptions.remove( invalidType );
		}

		return new Gson().fromJson( preference, UserPreferenceRtdbImpl.class );

	}

	
	
	
	
	// PREFERENCE Table

	@Override
	public UserPreferenceRtdb getUserPreference( Long userId )
			throws UnexpectedServerException {
		
		try {
			BlobEntry blobEntry = HttpUtil.doGet( DATABASE_URL + DATABASE_PREFERENCE_TABLE + "/" + userId + ".json", headersMap, null );
			String jsonStr = new String( blobEntry.getData(), "UTF-8" );
			if( jsonStr.equals( "null" ) )
				jsonStr = "{}";
			return _getUserPreferenceRtdb( jsonStr );
		} catch( UnsupportedEncodingException | JsonSyntaxException e ) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}
		
	}

	@Override
	public Map<Long, UserPreferenceRtdb> getUserPreferences( Collection<Long> userIdList )
			throws UnexpectedServerException {

		// TODO for Raghu: Using this approach you might end up pulling complete
		// database. Using memache with async UrlFetch instead.
		// https://cloud.google.com/appengine/docs/java/javadoc/com/google/appengine/api/urlfetch/URLFetchService
		
		if( userIdList == null || userIdList.isEmpty() )
			return new HashMap<>();

		Long startAt = Collections.min( userIdList );
		Long endAt = Collections.max( userIdList );

		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put( "orderBy", "\"" + "$key" + "\"" );
		paramsMap.put( "startAt", "\"" + startAt + "\"" );
		paramsMap.put( "endAt", "\"" + endAt + "\"" );
		
		Map<Long,UserPreferenceRtdb> userPreferences = _getUserPreferences( paramsMap );
		userPreferences.keySet().retainAll( userIdList );

		return userPreferences;

	}

	@Override
	public Map<Long, UserPreferenceRtdb> getUserPreferences( Date minLastUpdated ) 
			throws UnexpectedServerException {

		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put( "orderBy", "\"" + "lastUpdated" + "\"" );
		paramsMap.put( "startAt", minLastUpdated.getTime() + "" );
		return _getUserPreferences( paramsMap );

	}

	@Override
	public Map<Long, UserPreferenceRtdb> getUserPreferences( Integer maxAndroidVersionCode ) 
			throws UnexpectedServerException {

		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put( "orderBy", "\"" + "androidVersionCode" + "\"" );
		paramsMap.put( "endAt", maxAndroidVersionCode + "" );
		return _getUserPreferences( paramsMap );

	}

	private Map<Long, UserPreferenceRtdb> _getUserPreferences( Map<String, String> paramsMap )
			throws UnexpectedServerException {
		
		try {
			BlobEntry blobEntry = HttpUtil.doGet( DATABASE_URL + DATABASE_PREFERENCE_TABLE + ".json", headersMap, paramsMap );
			String jsonStr = new String( blobEntry.getData(), "UTF-8" );
			JsonObject json = new Gson().fromJson( jsonStr, JsonElement.class ).getAsJsonObject();
			Map<Long, UserPreferenceRtdb> userPreferenceMap = new HashMap<>();
			for( Entry<String, JsonElement> entry : json.entrySet() )
				userPreferenceMap.put( Long.parseLong( entry.getKey() ), _getUserPreferenceRtdb( entry.getValue().getAsJsonObject() ) );
			return userPreferenceMap;
		} catch( UnsupportedEncodingException | JsonSyntaxException e ) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}
	
	}

}
