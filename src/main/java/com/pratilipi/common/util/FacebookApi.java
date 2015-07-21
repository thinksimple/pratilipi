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
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;

public class FacebookApi {

	private static final Logger logger =
			Logger.getLogger( FacebookApi.class.getName() );

	
	private static final String GRAPH_API_2p2_URL = "https://graph.facebook.com/v2.2";
	private static final String GRAPH_API_2p4_URL = "https://graph.facebook.com/v2.4";
	
	
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
			String requestUrl = GRAPH_API_2p2_URL
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
	
	public static Map<String, Long> getUrlShareCount( List<String> urlList ) throws UnexpectedServerException {
		Gson gson = new Gson();
		int urlsPerRequest = 20;
		
		Map<String, Long> urlCountMap = new HashMap<>();

		for( int i = 0; i < urlList.size(); i = i + urlsPerRequest ) {
			String ids = "";
			for( int j = 0; i + j < urlList.size() && j < urlsPerRequest; j++ )
				ids = ids + urlList.get( i + j ) + ",";
			ids = ids.substring( 0, ids.length() - 1 );

			try{
				String requestUrl = GRAPH_API_2p4_URL
						+ "?ids=" + URLEncoder.encode( ids, "UTF-8" )
						+ "&access_token=" + getAccessToken();
				
				String responsePayload = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );
				JsonElement responseJson = gson.fromJson( responsePayload, JsonElement.class );
				
				for( int j = 0; i + j < urlList.size() && j < urlsPerRequest; j++ ) {
					JsonElement jsonElement = responseJson.getAsJsonObject().get( urlList.get( i + j ) );
					JsonElement shareCountJson = jsonElement.getAsJsonObject().get( "shares" );
					if( shareCountJson == null )
						urlCountMap.put( urlList.get( i + j ), 0L );
					else
						urlCountMap.put( urlList.get( i + j ), shareCountJson.getAsLong() );
				}
			} catch( IOException e ) {
				throw new UnexpectedServerException();
			}
		}
		
		return urlCountMap;
	} 
}
