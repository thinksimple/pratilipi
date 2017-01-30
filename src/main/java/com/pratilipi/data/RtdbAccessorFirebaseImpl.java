package com.pratilipi.data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
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
		// database. Using memache and/or async UrlFetch instead.
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
	
	// TODO: Remove it asap
	@SuppressWarnings("unused")
	@Override
	public Map<Long, UserPreferenceRtdb> getUserPreferences( String method, Integer limitTo ) 
			throws UnexpectedServerException {

		List<Long> userIds = new ArrayList<>( _getUserPreferences( null ).keySet() );
		if( limitTo != null )
			userIds = userIds.subList( 0, limitTo );

		if( method.equals( "1" ) ) {
			Long x = new Date().getTime();
			for( Long userId : userIds ) {
				BlobEntry blobEntry = HttpUtil.doGet( DATABASE_URL + DATABASE_PREFERENCE_TABLE + "/" + userId + ".json", headersMap, null );
			}
			Long y = new Date().getTime();
			logger.log( Level.INFO, "Old sequential method = " + ( y-x ) + " ms." );
		}
		if( method.equals( "2" ) ) {
			URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
			Long x = new Date().getTime();
			for( Long userId : userIds ) {
				HTTPResponse response;
				try {
					HTTPRequest req = new HTTPRequest( new URL( DATABASE_URL + DATABASE_PREFERENCE_TABLE + "/" + userId + ".json" ) );
					req.setHeader( new HTTPHeader( "Authorization", this.headersMap.get( "Authorization" ) ) );
					response = urlFetch.fetch( req );
					String resString = new String( response.getContent(), "UTF-8" );
				} catch( IOException e ) {
					logger.log( Level.SEVERE, "Failed fetching UserId: " + userId );
				}
			}
			Long y = new Date().getTime();
			logger.log( Level.INFO, "New sequential method = " + ( y-x ) + " ms." );
		}
		if( method.equals( "3" ) ) {
			URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
			Long x = new Date().getTime();
			try {
				List<Future<HTTPResponse>> responses = new ArrayList<>();
				for( Long userId : userIds ) {
					HTTPRequest req = new HTTPRequest( new URL( DATABASE_URL + DATABASE_PREFERENCE_TABLE + "/" + userId + ".json" ) );
					req.setHeader( new HTTPHeader( "Authorization", this.headersMap.get( "Authorization" ) ) );
					responses.add( urlFetch.fetchAsync( req ) );
				}
				for( Future<HTTPResponse> response : responses ) {
					String resString = new String( response.get().getContent(), "UTF-8" );
				}
			} catch ( IOException | InterruptedException | ExecutionException e ) {
				e.printStackTrace();
			}
			Long y = new Date().getTime();
			logger.log( Level.INFO, "New async method = " + ( y-x ) + " ms." );
		}
		return null;
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
