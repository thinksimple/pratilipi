package com.pratilipi.api;

import java.util.Date;
import java.util.UUID;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Get;
import com.claymus.commons.server.EncryptPassword;
import com.claymus.commons.shared.AccessTokenType;
import com.claymus.commons.shared.exception.IllegalArgumentException;
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
			throws IllegalArgumentException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );


		// Validating minimum requirements
		if( request.getUserId() == null && request.getPublisherId() == null )
			throw new IllegalArgumentException( "User/Publisher id is required." );

		else if( request.getUserId() != null && request.getPublisherId() != null )
			if( request.getUserSecret() == null && request.getPublisherSecret() == null )
				throw new IllegalArgumentException( "User/Publisher secret is required." );

		else if( request.getUserId() != null && request.getUserSecret() == null ) // && request.getPublisherId() == null
			throw new IllegalArgumentException( "User secret is required." );

		else if( request.getPublisherId() != null && request.getPublisherId() == null ) // && request.getUserId() == null
			throw new IllegalArgumentException( "Publisher secret is required." );

		
		// Fetching User and/or Publisher entities
		User user = null;
		Publisher publisher = null;
		
		if( request.getUserId() != null ) {
			String userId = request.getUserId();
			user = userId.indexOf( '@' ) == -1
					? dataAccessor.getUser( Long.parseLong( userId ) )
					: dataAccessor.getUserByEmail( userId );
			if( user == null )
				throw new IllegalArgumentException( "Invalid user id." );
		}

		if( request.getPublisherId() != null ) {
			publisher = dataAccessor.getPublisher( request.getPublisherId() );
			if( publisher == null )
				throw new IllegalArgumentException( "Invalid publisher id." );
		}
		
		
		// Authenticating request and creating AccessToken entity
		AccessToken accessToken = dataAccessor.newAccessToken();
		if( user != null && request.getUserSecret() != null ) {
			if( !EncryptPassword.check( request.getUserSecret(), user.getPassword() ) )
				throw new IllegalArgumentException( "Invalid user secret." );
			accessToken.setUserId( user.getId() );
			accessToken.setType( AccessTokenType.USER );
			
		} else if( publisher != null && request.getPublisherSecret() != null ) {
			if( !request.getPublisherSecret().equals( publisher.getSecret() ) )
				throw new IllegalArgumentException( "Invalid publisher secret." );
			
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
