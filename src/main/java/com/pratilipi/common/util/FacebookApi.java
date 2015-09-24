package com.pratilipi.common.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;

public class FacebookApi {

	private static final Logger logger =
			Logger.getLogger( FacebookApi.class.getName() );
	
	private static final String GRAPH_API_2p2_URL = "https://graph.facebook.com/v2.2";
	private static final String GRAPH_API_2p4_URL = "https://graph.facebook.com/v2.4";
	private static final String GRAPH_API_URL = "https://graph.facebook.com/";
	
	
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
	
	public static String getFbUserEmail( String fbUserId ) {
		String requestUrl = GRAPH_API_URL
				+ "?id=" + fbUserId 
				+ "&access_token="
				+ getAccessToken();
		String responsePayload = null;
		try {
			responsePayload = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );
		} catch ( IOException e ) {
			logger.log( Level.SEVERE, "Failed to access Graph Api.", e );		}
		return new Gson().fromJson( responsePayload, JsonElement.class ).getAsJsonObject().get( "email" ).getAsString();
	}
	
	public static Boolean validateUserAccessToken( String fbUserId, String fbUserEmail, String fbUserAccessToken )
			throws UnexpectedServerException {
		
		try {
			String requestUrl = GRAPH_API_URL
					+ "debug_token"
					+ "?input_token=" + fbUserAccessToken
					+ "&access_token=" + getAccessToken();
			String responsePayload = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );

			logger.log( Level.INFO, "Graph Api Request : " + requestUrl );
			logger.log( Level.INFO, "Graph Api Response : " + responsePayload );

			JsonObject responseJson = new Gson().fromJson( responsePayload, JsonElement.class ).getAsJsonObject();
			if( responseJson.get( "error" ) != null )
				return false;

			JsonObject responseDataJson = responseJson.get( "data" ).getAsJsonObject();
			
			if( fbUserEmail != null ) {
				return responseDataJson.get( "is_valid" ).getAsBoolean()
					&& responseDataJson.get( "user_id" ).equals( fbUserId )
					&& responseDataJson.get( "app_id" ).equals( getAppId() )
					&& getFbUserEmail( responseDataJson.get( "user_id" ).getAsString() ).equals( fbUserEmail );
			}
				
			return responseDataJson.get( "is_valid" ).getAsBoolean()
					&& responseDataJson.get( "user_id" ).equals( fbUserId )
					&& responseDataJson.get( "app_id" ).equals( getAppId() );

		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to access Graph Api.", e );
			throw new UnexpectedServerException();
		}
		
	}
	
	public static long getUrlShareCount( String url ) throws UnexpectedServerException {
		try {
			String requestUrl = GRAPH_API_2p2_URL
					+ "?id=" + URLEncoder.encode( url, "UTF-8" )
					+ "&access_token=" + getAccessToken();
			logger.log( Level.INFO, "Request URL: " + requestUrl );

			String responsePayload = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );
			logger.log( Level.INFO, "Response Payload: " + responsePayload );
			
			JsonElement responseJson = new Gson().fromJson( responsePayload, JsonElement.class );
			JsonElement shareJson = responseJson.getAsJsonObject().get( "share" );
			if( shareJson == null )
				return 0L;
			JsonElement shareCountJson = shareJson.getAsJsonObject().get( "share_count" );

			return shareCountJson != null ? shareCountJson.getAsLong() : 0L; 
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch data from Facebook.", e );
			throw new UnexpectedServerException();
		}
	}
	
	public static Map<String, Long> getUrlShareCount( List<String> urlList ) throws UnexpectedServerException {
		Gson gson = new Gson();
		int urlsPerRequest = 20;
		
		Map<String, Long> urlCountMap = new HashMap<>();

		for( int i = 0; i < urlList.size(); i = i + urlsPerRequest ) {
			String urls = "";
			for( int j = 0; i + j < urlList.size() && j < urlsPerRequest; j++ )
				urls = urls + urlList.get( i + j ) + ",";
			urls = urls.substring( 0, urls.length() - 1 );

			try{
				String requestUrl = GRAPH_API_2p4_URL
						+ "?ids=" + URLEncoder.encode( urls, "UTF-8" )
						+ "&access_token=" + getAccessToken();
				
				String responsePayload = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );
				JsonElement responseJson = gson.fromJson( responsePayload, JsonElement.class );
				
				for( int j = 0; i + j < urlList.size() && j < urlsPerRequest; j++ ) {
					JsonElement jsonElement = responseJson.getAsJsonObject().get( urlList.get( i + j ) );
					JsonElement shareJson = jsonElement.getAsJsonObject().get( "share" );
					if( shareJson != null ) {
						JsonElement shareCountJson = shareJson.getAsJsonObject().get( "share_count" );
						if( shareCountJson != null )
							urlCountMap.put( urlList.get( i + j ), shareCountJson.getAsLong() );
					}
				}
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch data from Facebook.", e );
				throw new UnexpectedServerException();
			}
		}
		
		return urlCountMap;
	} 
}
