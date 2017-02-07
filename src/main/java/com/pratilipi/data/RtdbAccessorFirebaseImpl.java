package com.pratilipi.data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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

	public RtdbAccessorFirebaseImpl( String googleApiAccessToken, Memcache memcache ) {
		this.headersMap = new HashMap<>();
		this.headersMap.put( "Authorization", "Bearer " + googleApiAccessToken );
		this.memcache = memcache;
	}


	private String _getMemcacheId( Long userId ) {
		return "Firebase.PREFERENCE." + userId;
	}

	
	private UserPreferenceRtdb _getUserPreferenceRtdb( String jsonStr ) {
		if( jsonStr.equals( "null" ) )
			jsonStr = "{}";
		return _getUserPreferenceRtdb( new Gson().fromJson( jsonStr, JsonElement.class ).getAsJsonObject() );
	}

	private UserPreferenceRtdb _getUserPreferenceRtdb( JsonObject json ) {

		if( json.has( "emailFrequency" ) ) {
			String emailFrequency = json.get( "emailFrequency" ).getAsString();
			if( emailFrequency.equals( "ONCE A DAY" ) ) {
				json.remove( "emailFrequency" );
				json.addProperty( "emailFrequency", EmailFrequency.DAILY.name() );
			} else if( emailFrequency.equals( "ONCE A WEEK" ) ) {
				json.remove( "emailFrequency" );
				json.addProperty( "emailFrequency", EmailFrequency.WEEKLY.name() );
			}
		}

		if( json.has( "notificationSubscriptions" ) ) {
			JsonObject notifSubscriptionsJson = json.get( "notificationSubscriptions" ).getAsJsonObject();
			List<String> invalidTypes = new ArrayList<>();
			for( Entry<String, JsonElement> entry : notifSubscriptionsJson.entrySet() ) {
				try {
					NotificationType.valueOf( entry.getKey() );
				} catch( IllegalArgumentException ex ) {
					invalidTypes.add( entry.getKey() );
				}
			}
			for( String invalidType : invalidTypes )
				notifSubscriptionsJson.remove( invalidType );
		}

		return new Gson().fromJson( json, UserPreferenceRtdbImpl.class );

	}


	// PREFERENCE Table

	@Override
	public UserPreferenceRtdb getUserPreference( Long userId )
			throws UnexpectedServerException {

		String jsonStr = memcache.get( _getMemcacheId( userId ) );
		if( jsonStr == null ) {
			try {
				BlobEntry blobEntry = HttpUtil.doGet( DATABASE_URL + DATABASE_PREFERENCE_TABLE + "/" + userId + ".json", headersMap, null );
				jsonStr = new String( blobEntry.getData(), "UTF-8" );
				memcache.put( _getMemcacheId( userId ), jsonStr, 5 );
			} catch( UnsupportedEncodingException | JsonSyntaxException e ) {
				logger.log( Level.SEVERE, e.getMessage() );
				throw new UnexpectedServerException();
			}
		}

		return _getUserPreferenceRtdb( jsonStr );

	}

	@Override
	public Map<Long, UserPreferenceRtdb> getUserPreferences( Collection<Long> userIds )
			throws UnexpectedServerException {

		if( userIds == null || userIds.isEmpty() )
			return new HashMap<>();

		Set<Long> userIdSet = new HashSet<>( userIds );

		Set<String> memcacheIds = new HashSet<>( userIdSet.size() );
		for( Long userId : userIdSet )
			memcacheIds.add( _getMemcacheId( userId ) );
		Map<String, String> memcacheDataMap = memcache.getAll( memcacheIds );

		List<Long> existingUserIdList = new ArrayList<>();
		Map<Long, UserPreferenceRtdb> userPreferences = new HashMap<>( userIdSet.size() );

		for( Long userId : userIdSet ) {
			String json = memcacheDataMap.get( _getMemcacheId( userId ) );
			if( json != null ) {
				userPreferences.put( userId, _getUserPreferenceRtdb( json ) );
				existingUserIdList.add( userId );
			}
		}

		userIdSet.removeAll( existingUserIdList );

		Map<Long, String> userIdUrlMap = new HashMap<>( userIdSet.size() );
		for( Long userId : userIdSet )
			userIdUrlMap.put( userId, DATABASE_URL + DATABASE_PREFERENCE_TABLE + "/" + userId + ".json" );


		Map<String, BlobEntry> responses = HttpUtil.doGet( userIdUrlMap.values(), this.headersMap );


		memcacheDataMap = new HashMap<>();
		for( Long userId : userIdSet ) {
			try {
				String json = new String( responses.get( userIdUrlMap.get( userId ) ).getData(), "UTF-8" );
				userPreferences.put( userId, _getUserPreferenceRtdb( json ) );
				memcacheDataMap.put( _getMemcacheId( userId ), json );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to parse response.", e );
				throw new UnexpectedServerException();
			}
		}

		if( ! memcacheDataMap.isEmpty() )
			memcache.putAll( memcacheDataMap, 5 );

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
			JsonObject jsonObject = new Gson().fromJson( jsonStr, JsonElement.class ).getAsJsonObject();
			Map<Long, UserPreferenceRtdb> userPreferenceMap = new HashMap<>();
			Map<String, String> memcacheDataMap = new HashMap<>();
			for( Entry<String, JsonElement> entry : jsonObject.entrySet() ) {
				Long userId = Long.parseLong( entry.getKey() );
				userPreferenceMap.put( userId, _getUserPreferenceRtdb( entry.getValue().getAsJsonObject() ) );
				memcacheDataMap.put( _getMemcacheId( userId ), entry.getValue().getAsJsonObject().toString() );
			}
			memcache.putAll( memcacheDataMap, 5 );
			return userPreferenceMap;
		} catch( UnsupportedEncodingException | JsonSyntaxException e ) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}

	}

}
