package com.pratilipi.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
	private static final String DATABASE_PREFERENCE_EMAIL_FREQUENCY = "emailFrequency";
	private static final String DATABASE_PREFERENCE_LAST_UPDATED = "lastUpdated";
	private static final String DATABASE_PREFERENCE_ANDROID_VERSION = "androidVersion";
	private static final String DATABASE_PREFERENCE_NOTIFICATION_SUBSCRIPTIONS = "notificationSubscriptions";

	
	private final Map<String, String> headersMap;
	private final Memcache memcache;

	private UserPreferenceRtdb _getUserPreferenceRtdb( String json ) {
		return _getUserPreferenceRtdb( new Gson().fromJson( json, JsonElement.class ).getAsJsonObject() );
	}

	private UserPreferenceRtdb _getUserPreferenceRtdb( JsonObject preference ) {

		if( preference.has( DATABASE_PREFERENCE_EMAIL_FREQUENCY ) ) {
			String emailFrequency = preference.get( DATABASE_PREFERENCE_EMAIL_FREQUENCY ).getAsString();
			if( emailFrequency.equals( "ONCE A DAY" ) ) {
				preference.remove( "emailFrequency" );
				preference.addProperty( "emailFrequency", EmailFrequency.DAILY.name() );
			} else if( emailFrequency.equals( "ONCE A WEEK" ) ) {
				preference.remove( "emailFrequency" );
				preference.addProperty( "emailFrequency", EmailFrequency.WEEKLY.name() );
			}
		}

		if( preference.has( DATABASE_PREFERENCE_NOTIFICATION_SUBSCRIPTIONS ) ) {
			JsonObject notificationSubscriptions = preference.get( DATABASE_PREFERENCE_NOTIFICATION_SUBSCRIPTIONS ).getAsJsonObject();
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
				BlobEntry blobEntry = HttpUtil.doGet( DATABASE_URL + DATABASE_PREFERENCE_TABLE + "/" + userId + ".json", headersMap, null );
				json = new String( blobEntry.getData(), "UTF-8" );
				if( json.equals( "null" ) )
					json = "{}";
				memcache.put( memcacheId, json, 5 );
			} catch( UnsupportedEncodingException | JsonSyntaxException e ) {
				logger.log( Level.SEVERE, e.getMessage() );
				throw new UnexpectedServerException();
			}
		}
		return _getUserPreferenceRtdb( json );
	}

	private Map<Long,UserPreferenceRtdb> getUserPreferences( Map<String, String> paramsMap ) throws UnexpectedServerException {

		Map<Long, UserPreferenceRtdb> userPreferenceMap = new HashMap<>();
		try {
			BlobEntry blobEntry = HttpUtil.doGet( DATABASE_URL + DATABASE_PREFERENCE_TABLE + ".json", headersMap, paramsMap );
			String json = new String( blobEntry.getData(), "UTF-8" );
			JsonObject usersPreferences = new Gson().fromJson( json, JsonElement.class ).getAsJsonObject();
			for( Entry<String, JsonElement> it : usersPreferences.entrySet() )
				userPreferenceMap.put( Long.parseLong( it.getKey() ), _getUserPreferenceRtdb( it.getValue().getAsJsonObject() ) );
		} catch( UnsupportedEncodingException | JsonSyntaxException e ) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}

		return userPreferenceMap;

	}

	@Override
	public Map<Long,UserPreferenceRtdb> getUserPreferences( Date minLastUpdated ) throws UnexpectedServerException {

		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put( "orderBy", "\"" + DATABASE_PREFERENCE_LAST_UPDATED + "\"" );
		paramsMap.put( "startAt", "\"" + minLastUpdated.getTime() + "\"" );
		return getUserPreferences( paramsMap );

	}

	@Override
	public Map<Long,UserPreferenceRtdb> getUserPreferences( String minAndroidVersion ) throws UnexpectedServerException {

		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put( "orderBy", "\"" + DATABASE_PREFERENCE_ANDROID_VERSION + "\"" );
		paramsMap.put( "startAt", "\"" + minAndroidVersion + "\"" );
		return getUserPreferences( paramsMap );

	}

}
