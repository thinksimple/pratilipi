package com.pratilipi.api;

import java.util.Date;
import java.util.UUID;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.data.transfer.AccessToken;
import com.pratilipi.api.shared.GetOAuthRequest;
import com.pratilipi.api.shared.GetOAuthResponse;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Publisher;


@SuppressWarnings("serial")
public class OAuthApi extends GenericApi {

	private static final long ACCESS_TOKEN_VALIDITY = 60 * 60 * 1000; // 1 Hr
	

	@Get
	public GetOAuthResponse getOAuth( GetOAuthRequest request )
			throws InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Publisher publisher = dataAccessor.getPublisher( request.getPublisherId() );
		
		if( publisher == null || !request.getPublisherSecret().equals( publisher.getPublisherSecret() ) )
			throw new InsufficientAccessException( "Invalid publisher id or secret." );
		

		String tokenId = UUID.randomUUID().toString();
		Date expiry = new Date( new Date().getTime() + ACCESS_TOKEN_VALIDITY );
		

		// Creating and persisting AccessToken entity
		AccessToken accessToken = dataAccessor.newAccessToken();
		accessToken.setId( tokenId );
		accessToken.setExpiry( expiry );
		accessToken.setValues( gson.toJson( request ) );
		accessToken = dataAccessor.createAccessToken( accessToken );

		
		return new GetOAuthResponse( tokenId , expiry.getTime() );
	}
	
}
