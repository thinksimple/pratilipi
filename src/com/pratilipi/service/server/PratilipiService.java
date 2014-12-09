package com.pratilipi.service.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.service.shared.GetPratilipiContentRequest;
import com.pratilipi.service.shared.GetPratilipiContentResponse;


@SuppressWarnings("serial")
public class PratilipiService extends HttpServlet {

	private static final Logger logger =
			Logger.getLogger( PratilipiService.class.getName() );

	private static final Gson gson = new GsonBuilder().create();

	private static final String RESOURCE_PRATILIPI_CONTENT_PRTAILIPI =
			"/pratilipi/content/pratilipi";
	private static final String RESOURCE_PRATILIPI_CONTENT_IMAGE =
			"/pratilipi/content/image";
	private static final String PRATILIPI_OAUTH = "/oauth";
	
	//BELOW VARIABLES ARE USED FOR TESTING PURPOSE
	private static final String PUBLISHER_ID = "5131259839774720";
	private static final String PUBLISHER_SECRET = "WythcpbjPW5DqPLWDRnjgETbLbZiUbTvGTgMR8QtzC0";

	private int accessTokenLength = 64;
	
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		String requestPayload = IOUtils.toString( request.getInputStream() );
		logger.log( Level.INFO, "Request Payload: " + requestPayload );

		JsonObject jsonObj = requestPayload == null || requestPayload.isEmpty()
				? new JsonObject()
				: gson.fromJson( requestPayload, JsonElement.class ).getAsJsonObject();
		Enumeration<String> queryParams = request.getParameterNames();
		while( queryParams.hasMoreElements() ) {
			String param = queryParams.nextElement();
			jsonObj.addProperty( param, request.getParameter( param ) );
		}

		requestPayload = jsonObj.toString();
		logger.log( Level.INFO, "Request Payload: " + requestPayload );

		String responsePayload = null;
		
		String resourceName = request.getRequestURI().substring( "/service.pratilipi".length() );
		try {
			
			if( resourceName.equals( RESOURCE_PRATILIPI_CONTENT_PRTAILIPI ) ) {
				GetPratilipiContentRequest apiRequest =
						gson.fromJson( requestPayload, GetPratilipiContentRequest.class );
				GetPratilipiContentResponse apiResponse =
						PratilipiContentHelper.getPratilipiContent( apiRequest, request );
				responsePayload = gson.toJson( apiResponse );
			
			} else if( resourceName.equals( RESOURCE_PRATILIPI_CONTENT_IMAGE ) ) {
				// TODO: implementation
			} else if( resourceName.equals( PRATILIPI_OAUTH ) ) {
				if( jsonObj.get( "publisherId" ).equals( PUBLISHER_ID ) && jsonObj.get( "publisherSecret" ).equals( PUBLISHER_SECRET ) ){
					//Generating Unique UserID
					String uuid = UUID.randomUUID().toString();
					
					//Generating 64 bytes access token
					byte[] accessTokenInBytes = SecureRandom.getInstance("SHA1PRNG").generateSeed( accessTokenLength );
					String accessTokenString = Base64.encodeBase64String( accessTokenInBytes );
					
					//Storing data received in http request and acces token generated in a json object
					JsonObject accessTokenJson = new JsonObject();
					accessTokenJson.addProperty( "publisherId", jsonObj.get( "publisher_id" ).toString() );
					accessTokenJson.addProperty( "publisherSecret", jsonObj.get( "publisherSecret" ).toString() );
					accessTokenJson.addProperty( "accessToken", accessTokenString );
					
					//Setting expiry time to 60 mins from token create time.
					Date expiry = new Date();
					expiry.setTime( expiry.getTime() + 60*60*1000 );
					
					//AccessToken entity
					AccessToken accessToken = dataAccessor.newAccessToken();
					accessToken.setUuid( uuid );
					accessToken.setExpiry( expiry );
					accessToken.setValues( accessTokenJson.toString() );
					//Persisting access token entity
					dataAccessor.createAccessToken( accessToken );
					
					responsePayload = gson.toJson( uuid );
				} else
					response.sendError( HttpServletResponse.SC_FORBIDDEN );
			}
			
		} catch( UnexpectedServerException | NoSuchAlgorithmException e ) {
			logger.log( Level.SEVERE, "Failed to execute API.", e );
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			return;
		}
		
		logger.log( Level.INFO, "Response Payload: " + responsePayload );
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter writer = response.getWriter();
		writer.println( responsePayload );
		writer.close();
	}
	
}
