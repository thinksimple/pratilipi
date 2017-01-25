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


	private final Map<String, String> headersMap;
	private final Memcache memcache;

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

	public RtdbAccessorFirebaseImpl( String googleApiAccessToken, Memcache memcache ) {
		this.headersMap = new HashMap<>();
		this.headersMap.put( "Authorization", "Bearer " + googleApiAccessToken );
		this.memcache = memcache;
	}


	// PREFERENCE Table

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

	@Override
	public Map<Long,UserPreferenceRtdb> getUserPreferences( Date minLastUpdated, String operator ) throws UnexpectedServerException {

		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put( "orderBy", "\"" + "lastUpdated" + "\"" );

		if( operator.isEmpty() || operator.equals( "=" ) ) {
			paramsMap.put( "equalTo", "\"" + minLastUpdated.getTime() + "\"" );
			return getUserPreferences( paramsMap );

		} else if( operator.equals( ">=" ) ) {
			paramsMap.put( "startAt", "\"" + minLastUpdated.getTime() + "\"" );
			return getUserPreferences( paramsMap );

		} else if( operator.equals( ">" ) ) {
			paramsMap.put( "startAt", "\"" + ( minLastUpdated.getTime() + 1 )  + "\"" );
			return getUserPreferences( paramsMap );

		} else if( operator.equals( "<=" ) ) {
			paramsMap.put( "endAt", "\"" + minLastUpdated.getTime() + "\"" );
			return getUserPreferences( paramsMap );

		} else if( operator.equals( "<" ) ) {
			paramsMap.put( "endAt", "\"" + ( minLastUpdated.getTime() - 1 )  + "\"" );
			return getUserPreferences( paramsMap );

		} else if( operator.equals( "!=" ) ) {
			Map<Long,UserPreferenceRtdb> userPreferenceMap = getUserPreferences( paramsMap );
			for( Map.Entry<Long, UserPreferenceRtdb> entry : userPreferenceMap.entrySet() ) {
				if( entry.getValue().getLastUpdated() != null && 
						entry.getValue().getLastUpdated().equals( minLastUpdated.getTime() ) )
					userPreferenceMap.remove( entry );
			}
			return userPreferenceMap;
		}

		return getUserPreferences( paramsMap );

	}

	@Override
	public Map<Long,UserPreferenceRtdb> getUserPreferences( String androidVersion, String operator ) throws UnexpectedServerException {

		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put( "orderBy", "\"" + "androidVersion" + "\"" );

		if( operator.isEmpty() || operator.equals( "=" ) ) {
			paramsMap.put( "equalTo", "\"" + androidVersion + "\"" );
			return getUserPreferences( paramsMap );

		} else if( operator.equals( ">=" ) ) {
			paramsMap.put( "startAt", "\"" + androidVersion + "\"" );
			return getUserPreferences( paramsMap );

		} else if( operator.equals( ">" ) ) {
			Map<Long,UserPreferenceRtdb> userPreferences = getUserPreferences( androidVersion, ">=" );
			userPreferences.keySet().removeAll( getUserPreferences( androidVersion, "=" ).keySet() );
			return userPreferences;

		} else if( operator.equals( "<=" ) ) {
			paramsMap.put( "endAt", "\"" + androidVersion + "\"" );
			return getUserPreferences( paramsMap );

		} else if( operator.equals( "<" ) ) {
			Map<Long,UserPreferenceRtdb> userPreferences = getUserPreferences( androidVersion, "<=" );
			userPreferences.keySet().removeAll( getUserPreferences( androidVersion, "=" ).keySet() );
			return userPreferences;

		} else if( operator.equals( "!=" ) ) {
			Map<Long,UserPreferenceRtdb> userPreferenceMap = getUserPreferences( paramsMap );
			for( Map.Entry<Long, UserPreferenceRtdb> entry : userPreferenceMap.entrySet() ) {
				if( entry.getValue().getAndroidVersion() != null && 
						entry.getValue().getAndroidVersion().equals( androidVersion ) )
					userPreferenceMap.remove( entry );
			}
			return userPreferenceMap;
		}

		return getUserPreferences( paramsMap );

	}

}
