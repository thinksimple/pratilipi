package com.pratilipi.common.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;

public class FacebookApi {

	private static final Logger logger =
			Logger.getLogger( FacebookApi.class.getName() );

	
	private static final String GRAPH_API_URL = "https://graph.facebook.com/v2.2";
	private static final String GRAPH_API_MULTIPLE_URL = "http://graph.facebook.com/";
	
	
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
	
	public static long getUrlShareCount( String url ) throws UnexpectedServerException {
		try {
			String requestUrl = GRAPH_API_URL
					+ "?id=" + URLEncoder.encode( url, "UTF-8" )
					+ "&access_token=" + getAccessToken();

			String responsePayload = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );
			logger.log( Level.INFO, "Response Payload: " + responsePayload );
			
			JsonElement responseJson = new Gson().fromJson( responsePayload, JsonElement.class );
			JsonElement shareJson = responseJson.getAsJsonObject().get( "share" );
			if( shareJson == null )
				return 0L;
			JsonElement shareCountJson = shareJson.getAsJsonObject().get( "share_count" );

			return shareCountJson != null ? shareCountJson.getAsLong() : 0L; 
		} catch( IOException e ) {
			throw new UnexpectedServerException();
		}
	}
	
	public static Map<String, Long> getUrlShareCount( String[] url ) throws UnexpectedServerException {
		
		int max_limit = 20;
		Map <String, Long> facebookShareCount = new HashMap <String, Long> ();
		int batches_count = (url.length % max_limit == 0) ? url.length / max_limit : url.length / max_limit + 1;
		
		for( int limit = 0; limit < batches_count ; limit ++ ) {
			// Formation of string
			String finalUrl = "";
			int start_index = limit * max_limit; 
			for( int temp =  0; temp < Math.min ( max_limit, url.length - (limit * max_limit) ); temp ++ ) {
				finalUrl = finalUrl + url[ temp + start_index ] + ",";
			}	
			finalUrl = finalUrl.substring( 0, finalUrl.length() - 1 );
			try{
				String requestUrl = GRAPH_API_MULTIPLE_URL
						+ "?ids=" + URLEncoder.encode( finalUrl, "UTF-8" );
				
				String responsePayload = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );
				JsonElement responseJson = new Gson().fromJson( responsePayload, JsonElement.class );
				
				for( int temp =  0; temp < Math.min ( max_limit, url.length - (limit * max_limit) ); temp ++ ) {
					JsonElement jsonElement = responseJson.getAsJsonObject().get( url [ temp + start_index ] );
					JsonElement shareCountJson = jsonElement.getAsJsonObject().get( "shares" );
					if ( shareCountJson == null )
						facebookShareCount.put( url [ temp + start_index ], 0L );
					else
						facebookShareCount.put( url [ temp + start_index ], shareCountJson.getAsLong() );
				}
			} catch( IOException e ) {
				throw new UnexpectedServerException();
			}
		}
		
			return facebookShareCount;
	} 
}
