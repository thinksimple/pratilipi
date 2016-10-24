package com.pratilipi.common.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.AppProperty;

public class GoogleApi {

	private static final Logger logger =
			Logger.getLogger( GoogleApi.class.getName() );

	private static GoogleIdTokenVerifier idTokenVerifier;

	
	private static HttpRequestInitializer getCredential( Collection<String> scopes ) {
		return new AppIdentityCredential( scopes ); // Works only on Google AppEngine
	}

	private static String getWebClientId() {
		return DataAccessorFactory.getDataAccessor()
				.getAppProperty( AppProperty.GOOGLE_WEB_CLIENT_ID )
				.getValue();
	}

	
	public static GoogleIdTokenVerifier getIdTokenVerifier() {
		if( idTokenVerifier == null )
			 idTokenVerifier = new GoogleIdTokenVerifier
					.Builder( new NetHttpTransport(), new JacksonFactory() )
					.setAudience( Arrays.asList( getWebClientId() ) )
					.setIssuer( "accounts.google.com" )
					.build();
		return idTokenVerifier;
	}
	
	public static Analytics getAnalytics( Collection<String> scopes )
			throws UnexpectedServerException {
		
		try {
			return new Analytics.Builder(
					GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(),
					getCredential( scopes ) )
				.setApplicationName( "Pratilipi" )
				.build();
		} catch( GeneralSecurityException | IOException e ) {
			logger.log( Level.SEVERE, "Failed to create Analytics Service Object.", e );
			throw new UnexpectedServerException();
		}
		
	}

	
	public static UserData getUserData( String googleIdToken )
			throws InvalidArgumentException, UnexpectedServerException {

		try {

			GoogleIdToken idToken = getIdTokenVerifier().verify( googleIdToken );
			if( idToken == null || idToken.getPayload() == null || ! idToken.getPayload().getAuthorizedParty().equals( getWebClientId() ) ) {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty( "googleIdToken", "Invalid GoogleIdToken !" );
				throw new InvalidArgumentException( jsonObject );
			}

			Payload payload = idToken.getPayload();
			logger.log( Level.INFO, "GoogleApi Payload : " + new Gson().toJson( payload ) );

			UserData userData = new UserData();
			userData.setGoogleId( payload.getSubject() );
			userData.setFirstName( (String) payload.get( "given_name" ) );
			userData.setLastName( (String) payload.get( "family_name" ) );
			userData.setEmail( payload.getEmail() );

			return userData;

		} catch( GeneralSecurityException | IOException e ) {
			logger.log( Level.SEVERE, "Google id token verification failed: " + e );
			throw new UnexpectedServerException();
		}

	}

}
