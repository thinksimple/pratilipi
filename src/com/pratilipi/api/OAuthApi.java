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
import com.pratilipi.api.shared.GetOAuthRequest;
import com.pratilipi.api.shared.GetOAuthResponse;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Publisher;


@SuppressWarnings("serial")
public class OAuthApi extends GenericApi {

	private static final long ACCESS_TOKEN_VALIDITY = 60 * 60 * 1000; // 1 Hr
	
	@Override
	protected void executeGet(
			JsonObject requestPayloadJson,
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException, UnexpectedServerException {
		
		GetOAuthRequest apiRequest =
				gson.fromJson( requestPayloadJson, GetOAuthRequest.class );
		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Publisher publisher = dataAccessor.getPublisher( apiRequest.getPublisherId() );
		
		if( publisher == null || !apiRequest.getPublisherSecret().equals( publisher.getPublisherSecret() ) ) {
			response.sendError( HttpServletResponse.SC_UNAUTHORIZED );
			return;
		}
		

		String tokenId = UUID.randomUUID().toString();
		Date expiry = new Date( new Date().getTime() + ACCESS_TOKEN_VALIDITY );
		

		// Creating and persisting AccessToken entity
		AccessToken accessToken = dataAccessor.newAccessToken();
		accessToken.setId( tokenId );
		accessToken.setExpiry( expiry );
		accessToken.setValues( requestPayloadJson.toString() );
		accessToken = dataAccessor.createAccessToken( accessToken );

		
		GetOAuthResponse apiResponse = new GetOAuthResponse( tokenId , expiry.getTime() );
		serveJson( gson.toJson( apiResponse ), request, response );
	}
	
}
