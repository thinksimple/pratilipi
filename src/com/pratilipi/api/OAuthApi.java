package com.pratilipi.api;

import java.util.Date;
import java.util.UUID;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Get;
import com.claymus.commons.server.EncryptPassword;
import com.claymus.commons.shared.AccessTokenType;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.User;
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
			throws InvalidArgumentException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );


		// Validating minimum requirements
		if( request.getUserId() == null && request.getPublisherId() == null ) {
			throw new InvalidArgumentException( "User/Publisher id is required." );

		} else if( request.getUserId() != null && request.getPublisherId() != null ) {
			if( request.getUserSecret() == null && request.getPublisherSecret() == null )
				throw new InvalidArgumentException( "User/Publisher secret is required." );

		} else if( request.getUserId() != null && request.getUserSecret() == null ) { // && request.getPublisherId() == null
			throw new InvalidArgumentException( "User secret is required." );
	
		} else if( request.getPublisherId() != null && request.getPublisherSecret() == null ) { // && request.getUserId() == null
			throw new InvalidArgumentException( "Publisher secret is required." );
		}
		
		
		// Fetching User and/or Publisher entities
		User user = null;
		Publisher publisher = null;
		
		if( request.getUserId() != null ) {
			user = dataAccessor.getUserByEmail( request.getUserId() );
			if( user == null )
				throw new InvalidArgumentException( "Invalid user id." );
		}

		if( request.getPublisherId() != null ) {
			publisher = dataAccessor.getPublisher( request.getPublisherId() );
			if( publisher == null )
				throw new InvalidArgumentException( "Invalid publisher id." );
		}
		
		
		// Authenticating request and creating AccessToken entity
		AccessToken accessToken = dataAccessor.newAccessToken();
		if( user != null && request.getUserSecret() != null ) {
			if( !EncryptPassword.check( request.getUserSecret(), user.getPassword() ) )
				throw new InvalidArgumentException( "Invalid user secret." );
			accessToken.setUserId( user.getId() );
			accessToken.setType( AccessTokenType.USER );
			
		} else if( publisher != null && request.getPublisherSecret() != null ) {
			if( !request.getPublisherSecret().equals( publisher.getSecret() ) )
				throw new InvalidArgumentException( "Invalid publisher secret." );
			
			accessToken.setPublisherId( publisher.getId() );
			if( user == null ) {
				accessToken.setType( AccessTokenType.PUBLISHER );
			} else {
				accessToken.setUserId( user.getId() );
				accessToken.setType( AccessTokenType.USER_PUBLISHER );
			}
			
		}
		accessToken.setId( UUID.randomUUID().toString() );
		accessToken.setExpiry( new Date( new Date().getTime() + ACCESS_TOKEN_VALIDITY ) );
		accessToken = dataAccessor.createAccessToken( accessToken );

		
		return new GetOAuthResponse( accessToken.getId() , accessToken.getExpiry().getTime() );
	}
	
}
