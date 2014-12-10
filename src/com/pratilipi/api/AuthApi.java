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
import com.pratilipi.data.transfer.Publisher;


@SuppressWarnings("serial")
public class AuthApi extends GenericApi {

	private static final long ACCESS_TOKEN_VALIDITY = 60 * 60 * 1000; // 1 Hr
	
	@Override
	protected void executeGet(
			JsonObject requestPayloadJson,
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		long publisherId = requestPayloadJson.get( "publisherId" ).getAsLong();
		String publisherSecret = requestPayloadJson.get( "publisherSecret" ).getAsString();
		
		Publisher publisher = dataAccessor.getPublisher( publisherId );
		
		if( publisher == null ){
			response.sendError( HttpServletResponse.SC_BAD_REQUEST );
			return;
		}else if( publisherId != publisher.getId() 
					|| !publisherSecret.equals( publisher.getPublisherSecret()  ) ) {
			response.sendError( HttpServletResponse.SC_UNAUTHORIZED );
			return;
		}
		

		String tokenId = UUID.randomUUID().toString();
		Date expiry = new Date( new Date().getTime() + ACCESS_TOKEN_VALIDITY );
		
		JsonObject values = new JsonObject();
		values.addProperty( "publisherId", publisherId );
		values.addProperty( "publisherSecret", publisherSecret );
		
		
		// Creating and persisting AccessToken entity
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
