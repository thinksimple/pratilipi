package com.pratilipi.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Gender;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.AppProperty;

public class FacebookApi {

	private static final Logger logger =
			Logger.getLogger( FacebookApi.class.getName() );
	
	private static final String GRAPH_API_2p2_URL = "https://graph.facebook.com/v2.2";
	private static final String GRAPH_API_2p4_URL = "https://graph.facebook.com/v2.4";
	private static final String FACEBOOK_RESCRAPE_URL = "https://graph.facebook.com:443";
	
	public static String getAppId() {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Map<String, String> facebookCredentials = dataAccessor.getAppProperty( AppProperty.FACEBOOK_CREDENTIALS ).getValue();
		return facebookCredentials.get( "appId" );
	}
	
	public static String getAccessToken() {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Map<String, String> facebookCredentials = dataAccessor.getAppProperty( AppProperty.FACEBOOK_CREDENTIALS ).getValue();
		return facebookCredentials.get( "appId" ) + "|" + facebookCredentials.get( "appSecret" );
	}
	
	
	public static Boolean validateUserAccessToken( String userAccessToken ) 
			throws UnexpectedServerException {
		
		Map<String, String> keyValueParameters = new HashMap<String, String>();
		keyValueParameters.put( "input_token", userAccessToken );
		keyValueParameters.put( "access_token", getAccessToken() );
		String responsePayload = NetworkCallsUtil.makeGetCall( GRAPH_API_2p2_URL + "/debug_token", keyValueParameters );
		
		JsonObject responseJson = new Gson().fromJson( responsePayload, JsonElement.class ).getAsJsonObject();
		return responseJson.get( "error" ) == null
				&& responseJson.get( "data" ) != null
				&& responseJson.get( "data" ).getAsJsonObject().get( "app_id" ).getAsString().equals( getAppId() );
		
	}
	
	public static UserData getUserData( String fbUserAccessToken )
			throws UnexpectedServerException {

		Map<String, String> keyValueParameters = new HashMap<String, String>();
		keyValueParameters.put( "access_token", getAccessToken() );
		keyValueParameters.put( "fields", "id,first_name,last_name,gender,birthday,email" );
		
		String responsePayload = NetworkCallsUtil.makeGetCall( GRAPH_API_2p4_URL + "/me", keyValueParameters );
		JsonObject responseJson = new Gson().fromJson( responsePayload, JsonElement.class ).getAsJsonObject();
		if( responseJson.get( "error" ) != null ) {
			// TODO: InvalidArgumentException if fbUserAccessToken is invalid or expired.
			logger.log( Level.SEVERE, "Error response from Graph Api." );
			throw new UnexpectedServerException();
		} else {
			UserData userData = new UserData( responseJson.get( "id" ).getAsString() );
			userData.setFirstName( responseJson.get( "first_name" ).getAsString() );
			userData.setLastName( responseJson.get( "last_name" ).getAsString() );
			userData.setGender( Gender.valueOf( responseJson.get( "gender" ).getAsString().toUpperCase() ) );
			
			if( responseJson.get( "email" ) != null )
				userData.setEmail( responseJson.get( "email" ).getAsString() );
			
			if( responseJson.get( "birthday" ) != null ) {
				try {
					userData.setDateOfBirth( new SimpleDateFormat( "MM/dd/yyyy" ).parse( responseJson.get( "birthday" ).getAsString() ) );
				} catch ( ParseException e ) {
					logger.log( Level.SEVERE, "Failed to parse Date of Birth.", e );
				}
			}
			
			return userData;
		}
			
	}
	
	
	public static long getUrlShareCount( String url )
			throws UnexpectedServerException {
		
		Map<String, String> keyValueParameters = new HashMap<String, String>();
		keyValueParameters.put( "id", url );
		keyValueParameters.put( "access_token", getAccessToken() );
		
		String responsePayload = NetworkCallsUtil.makeGetCall( GRAPH_API_2p2_URL, keyValueParameters );
		
		JsonElement responseJson = new Gson().fromJson( responsePayload, JsonElement.class );
		JsonElement shareJson = responseJson.getAsJsonObject().get( "share" );
		if( shareJson == null )
			return 0L;
		JsonElement shareCountJson = shareJson.getAsJsonObject().get( "share_count" );

		return shareCountJson != null ? shareCountJson.getAsLong() : 0L; 
	}
	
	public static Map<String, Long> getUrlShareCount( List<String> urlList ) throws UnexpectedServerException {
		
		int urlsPerRequest = 20;
		
		Map<String, Long> urlCountMap = new HashMap<>();

		for( int i = 0; i < urlList.size(); i = i + urlsPerRequest ) {
			String urls = "";
			for( int j = 0; i + j < urlList.size() && j < urlsPerRequest; j++ )
				urls = urls + urlList.get( i + j ) + ",";
			urls = urls.substring( 0, urls.length() - 1 );

			Map<String, String> keyValueParameters = new HashMap<String, String>();
			keyValueParameters.put( "ids", urls );
			keyValueParameters.put( "access_token", getAccessToken() );
			
			String responsePayload = NetworkCallsUtil.makeGetCall( GRAPH_API_2p4_URL, keyValueParameters );
			JsonElement responseJson = new Gson().fromJson( responsePayload, JsonElement.class );
			
			for( int j = 0; i + j < urlList.size() && j < urlsPerRequest; j++ ) {
				JsonElement jsonElement = responseJson.getAsJsonObject().get( urlList.get( i + j ) );
				JsonElement shareJson = jsonElement.getAsJsonObject().get( "share" );
				if( shareJson != null ) {
					JsonElement shareCountJson = shareJson.getAsJsonObject().get( "share_count" );
					if( shareCountJson != null )
						urlCountMap.put( urlList.get( i + j ), shareCountJson.getAsLong() );
				}
			}
			
		}
		
		return urlCountMap;
	}
	
	public static void sendScrapeRequestToFacebook( String link ) 
			throws UnexpectedServerException {
		
		Map<String, String> keyValueParameters = new HashMap<String, String>();
		keyValueParameters.put( "id", link );
		keyValueParameters.put( "access_token", getAccessToken() );
		String responsePayload = NetworkCallsUtil.makePostCall( FACEBOOK_RESCRAPE_URL, keyValueParameters );
		
		JsonObject responseJson = new Gson().fromJson( responsePayload, JsonObject.class );
		if( responseJson.get( "error" ) == null ) {
			logger.log( Level.INFO, "Facebook scrapping for link : " + link + " completed successfully." );
		} else {
			logger.log( Level.SEVERE, "Facebook scrapping for link : " + link + " failed with error message : " + responseJson.get( "error" ) );
			throw new UnexpectedServerException();
		}
	}
}
