package com.pratilipi.api.user.shared;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class FacebookValidation {
	private String appId, appSecret;
	
	private final Logger logger = Logger.getLogger( FacebookValidation.class.getName() );

	private String validateTokensEndpoint = "https://graph.facebook.com/debug_token?";
	
	public FacebookValidation( Map<String, String> facebookCredentials ){
		this.appId = facebookCredentials.get( "appId" );
		this.appSecret = facebookCredentials.get( "appSecret" );
	}
	
	public Boolean validateAccessToken( String socialId, String accessToken ) 
			throws IOException{
		
		// URL Formation
		String appAccessToken = appId + "|" + appSecret;
		String urlParameters = URLEncoder.encode( "input_token", "UTF-8" ) + "=" + URLEncoder.encode( accessToken, "UTF-8" )
					+ "&" + URLEncoder.encode( "access_token", "UTF-8" ) + "=" + URLEncoder.encode( appAccessToken, "UTF-8" );
		String requestUrl = validateTokensEndpoint + urlParameters;
		
		// URL Call
		String responsePayload = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );
		logger.log( Level.INFO, "Facebook Response : " + responsePayload );
		
		// Converting to JsonElement
		JsonElement responseJson = new Gson().fromJson( responsePayload, JsonElement.class ).getAsJsonObject().get( "data" );
		
		// Checking for "error"
		if( responsePayload.contains( "error" ) ) {
			String errorMessage = responseJson.getAsJsonObject().get( "error" ).getAsJsonObject().get( "message" ).getAsString();
			logger.log( Level.SEVERE, "Error returned by Facebook token end point : " + errorMessage );
			return false;
		}
		
		// Extracting data
		String responseAppId = responseJson.getAsJsonObject().get( "app_id" ).getAsString();
		Boolean responseIsValid = responseJson.getAsJsonObject().get( "is_valid" ).getAsBoolean();
		String responseUserId = responseJson.getAsJsonObject().get( "user_id" ).getAsString();
		
		// Validating data
		if( responseIsValid && responseUserId.equals( socialId ) && responseAppId.equals( this.appId ) )
			return true;
		else {
			logger.log( Level.SEVERE, "Facebook accessToken authentication failed." );
			return false;
		}	
	}
}
