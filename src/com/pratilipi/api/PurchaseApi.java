package com.pratilipi.api;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.api.GenericApi;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pratilipi.commons.shared.SellerType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.UserPratilipi;


@SuppressWarnings("serial")
public class PurchaseApi extends GenericApi {

	
	@Override
	protected void executeGet(
			JsonObject requestPayloadJson,
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException, UnexpectedServerException {
		
		String accessToken = requestPayloadJson.get( "accessToken" ).getAsString();
		String userEmail = requestPayloadJson.get( "userId" ).getAsString(); 	//userId send will be user email.
		long bookId = requestPayloadJson.get( "bookId" ).getAsLong();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		//Access Token verification
		AccessToken accessTokenEntity = dataAccessor.getAccessToken( accessToken );
		if( accessTokenEntity == null  ){
			response.sendError( HttpServletResponse.SC_BAD_REQUEST, "INVALID ACCESS TOKEN" );
			return;
		} else if ( accessTokenEntity != null && accessTokenEntity.getExpiry().before( new Date() )){
			response.sendError( HttpServletResponse.SC_BAD_REQUEST, "ACCESS TOKEN EXPIRED" );
			//TODO : REFRESH USER WEBPAGE TO START AUTHNETICATION PROCESS
			return;
		} 
		
		//BOOK VERIFICATION : bookId exist in database
		Pratilipi pratilipi = dataAccessor.getPratilipi( bookId );
		if( pratilipi == null ){
			response.sendError( HttpServletResponse.SC_BAD_REQUEST, "INVALID BOOKID" );
			return;
		}
		
		//Checking if book belongs to the publisher
		JsonParser parser = new JsonParser();
		JsonObject accessTokenValue = parser.parse( accessTokenEntity.getValues() ).getAsJsonObject();
		Long publisherId = accessTokenValue.get( "publisherId" ).getAsLong();
		if( (long) pratilipi.getPublisherId() != (long) publisherId ){
			response.sendError( HttpServletResponse.SC_BAD_REQUEST, "BOOK DOESNOT BELONG TO THE PUBLISHER" );
			return;
		}
		
		
		
		//User email verification. In case email doesnot exists in database, new user is created.
		User user = dataAccessor.getUserByEmail( userEmail );
		if( user == null ){
			user = dataAccessor.newUser();
			user.setEmail( userEmail );
			user.setSignUpDate( new Date() );
			user.setCampaign( "B2B" );
			
			dataAccessor.createOrUpdateUser( user );
		}
		
		//Update UserPratilipi Entity
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( user.getId(), pratilipi.getId() );
		if( userPratilipi == null ){
			//When userpratilipi entity doesnot exists for userId and pratilipiId pair send in the request.
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setPratilipiId( bookId );
			userPratilipi.setUserId( user.getId() );
		}

		userPratilipi.setPurchasedFrom( SellerType.PUBLISHER );
		userPratilipi.setPurchaseDate( new Date() );
		
		dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
		
		JsonObject returnObj = new JsonObject();
		returnObj.addProperty( "transactionId", userPratilipi.getId() );
		
		serveJson( gson.toJson( returnObj ), request, response );
		
	}
	
}
