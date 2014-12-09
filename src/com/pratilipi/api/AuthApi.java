package com.pratilipi.api;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.api.GenericApi;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.transfer.AccessToken;
import com.google.gson.JsonObject;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;


@SuppressWarnings("serial")
public class AuthApi extends GenericApi {

	private static final long ACCESS_TOKEN_VALIDITY = 60 * 60 * 1000; // 1 Hr
	
	private static final long PUBLISHER_ID = 5131259839774720L;
	private static final String PUBLISHER_SECRET = "WythcpbjPW5DqPLWDRnjgETbLbZiUbTvGTgMR8QtzC0";

	
	@Override
	protected void executeGet(
			JsonObject requestPayloadJson,
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException, UnexpectedServerException {
		
		long publisherId = requestPayloadJson.get( "publisherId" ).getAsLong();
		String publisherSecret = requestPayloadJson.get( "publisherSecret" ).getAsString();
		
		
		if( publisherId != PUBLISHER_ID || !publisherSecret.equals( PUBLISHER_SECRET ) ) {
			response.sendError( HttpServletResponse.SC_UNAUTHORIZED );
			return;
		}
		

		String tokenId = UUID.randomUUID().toString();
		Date expiry = new Date( new Date().getTime() + ACCESS_TOKEN_VALIDITY );
		
		JsonObject values = new JsonObject();
		values.addProperty( "publisherId", publisherId );
		values.addProperty( "publisherSecret", publisherSecret );
		
		
		// Creating and persisting AccessToken entity
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		AccessToken accessToken = dataAccessor.newAccessToken();
		accessToken.setUuid( tokenId );
		accessToken.setExpiry( expiry );
		accessToken.setValues( values.toString() );

		dataAccessor.createAccessToken( accessToken );

		
		JsonObject returnObj = new JsonObject();
		returnObj.addProperty( "accessToken", tokenId );
		returnObj.addProperty( "expiry", expiry.toString() );
		
		serveJson( gson.toJson( returnObj ), request, response );
		
	}
	
}
