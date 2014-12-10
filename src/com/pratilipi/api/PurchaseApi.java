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
import com.pratilipi.api.shared.PutPurchaseRequest;
import com.pratilipi.api.shared.PutPurchaseResponse;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.UserPratilipi;


@SuppressWarnings("serial")
public class PurchaseApi extends GenericApi {

	@Override
	protected void executePut(
			JsonObject requestPayloadJson,
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException, UnexpectedServerException {
		
		PutPurchaseRequest apiRequest = gson.fromJson( requestPayloadJson, PutPurchaseRequest.class );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		//Access Token verification
		AccessToken accessTokenEntity = dataAccessor.getAccessToken( apiRequest.getAccessToken() );
		if( accessTokenEntity == null ){
			response.sendError( HttpServletResponse.SC_BAD_REQUEST, "ACCESS TOKEN IS INVALID OR EXPIRED" );
			return;
		}
		
		//BOOK VERIFICATION : bookId exist in database
		Pratilipi pratilipi = dataAccessor.getPratilipi( apiRequest.getBookId() );
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
		User user = dataAccessor.getUserByEmail( apiRequest.getUserEmail() );
		if( user == null ){
			user = dataAccessor.newUser();
			user.setEmail( apiRequest.getUserEmail() );
			user.setSignUpDate( new Date() );
			user.setCampaign( "B2B" );
			
			dataAccessor.createOrUpdateUser( user );
		}
		
		//Update UserPratilipi Entity
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( user.getId(), pratilipi.getId() );
		if( userPratilipi == null ){
			//When userpratilipi entity doesnot exists for userId and pratilipiId pair send in the request.
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setPratilipiId( apiRequest.getBookId() );
			userPratilipi.setUserId( user.getId() );
		}

		userPratilipi.setPurchasedFrom( SellerType.PUBLISHER );
		userPratilipi.setPurchaseDate( new Date() );
		
		dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
		
		PutPurchaseResponse apiResponse = new PutPurchaseResponse( userPratilipi.getId() );
		
		serveJson( gson.toJson( apiResponse ), request, response );
		
	}
	
}
