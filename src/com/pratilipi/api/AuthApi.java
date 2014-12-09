package com.pratilipi.api;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.api.GenericApi;
import com.claymus.commons.server.GenerateAccessTokenUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.transfer.AccessToken;
import com.google.gson.JsonObject;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;


@SuppressWarnings("serial")
public class AuthApi extends GenericApi {

	//BELOW VARIABLES ARE USED FOR TESTING PURPOSE
	private static final String PUBLISHER_ID = "5131259839774720";
	private static final String PUBLISHER_SECRET = "WythcpbjPW5DqPLWDRnjgETbLbZiUbTvGTgMR8QtzC0";

	
	@Override
	protected void executeGet(
			String resourceName,
			JsonObject requestPayloadJson,
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException, UnexpectedServerException {
		
		if( requestPayloadJson.get( "publisherId" ).getAsString().equals( PUBLISHER_ID ) && 
				requestPayloadJson.get( "publisherSecret" ).getAsString().equals( PUBLISHER_SECRET ) ){
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
			//Generating Unique UserID
			String uuid = UUID.randomUUID().toString();
			
			//Generating 64 bytes access token
			GenerateAccessTokenUtil accessTokenUtil = 
					new GenerateAccessTokenUtil(requestPayloadJson.get( "publisherId" ).toString(),
												requestPayloadJson.get( "publisherSecret" ).toString() 
												);
			JsonObject accessTokenValue = accessTokenUtil.generateAccessToken();
			//Setting expiry time to 60 mins from token create time.
			Date expiry = new Date();
			expiry.setTime( expiry.getTime() + 60*60*1000 );
			
			//AccessToken entity
			AccessToken accessToken = dataAccessor.newAccessToken();
			accessToken.setUuid( uuid );
			accessToken.setExpiry( expiry );
			accessToken.setValues( accessTokenValue.toString() );
			
			//Persisting access token entity
			dataAccessor.createAccessToken( accessToken );
			
			JsonObject returnObj = new JsonObject();
			returnObj.addProperty( "accessToken", accessTokenValue.get("accessToken").getAsString() );
			returnObj.addProperty( "expiry", expiry.toString() );
			
			serveJson( gson.toJson( returnObj ), request, response );
		} else {
			response.sendError( HttpServletResponse.SC_FORBIDDEN );
		}
		
	}
	
}
