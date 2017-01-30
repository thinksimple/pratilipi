package com.pratilipi.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
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

		try {
			BlobEntry blobEntry = HttpUtil.doGet( DATABASE_URL + DATABASE_PREFERENCE_TABLE + "/" + userId + ".json", headersMap, null );
			return _getUserPreferenceRtdb( new String( blobEntry.getData(), "UTF-8" ) );
		} catch( UnsupportedEncodingException | JsonSyntaxException e ) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}

	}

	@Override
	public Map<Long, UserPreferenceRtdb> getUserPreferences( Collection<Long> userIdList )
			throws UnexpectedServerException {

		if( userIdList == null || userIdList.isEmpty() )
			return new HashMap<>();

		Map<Long, String> userIdUrlMap = new HashMap<>( userIdList.size() );
		for( Long userId : userIdList )
			userIdUrlMap.put( userId, DATABASE_URL + DATABASE_PREFERENCE_TABLE + "/" + userId + ".json" );

		Map<String, String> responses = HttpUtil.doGet( userIdUrlMap.values(), this.headersMap, null );

		Map<Long, UserPreferenceRtdb> userPreferences = new HashMap<>( userIdList.size() );
		for( Long userId : userIdList )
			userPreferences.put( userId, _getUserPreferenceRtdb( responses.get( userIdUrlMap.get( userId ) ) ) );

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
