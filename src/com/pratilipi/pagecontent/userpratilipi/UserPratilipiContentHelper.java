package com.pratilipi.pagecontent.userpratilipi;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.User;
import com.claymus.pagecontent.PageContentHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.commons.shared.BookmarkRequestType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.data.transfer.shared.UserPratilipiData;
import com.pratilipi.pagecontent.userpratilipi.gae.UserPratilipiContentEntity;
import com.pratilipi.pagecontent.userpratilipi.shared.UserPratilipiContentData;

public class UserPratilipiContentHelper extends PageContentHelper<
		UserPratilipiContent,
		UserPratilipiContentData,
		UserPratilipiContentProcessor>{
	
	private static final Gson gson = new GsonBuilder().create();
	

	@Override
	public String getModuleName() {
		return "User-Pratilipi";
	}

	@Override
	public Double getModuleVersion() {
		return 5.0;
	}
	

	public static UserPratilipiContent newUserPratilipiContent() {
		return new UserPratilipiContentEntity();
	}

	
	public static Boolean hasRequestAccessToAddBookmarks( HttpServletRequest request ){
		AccessToken accessToken = ( AccessToken ) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		if( accessToken.getUserId() == 0L )
			return false;
		else
			return true;
	}

	
	public static UserPratilipiData createUserPratilipiData( Long pratilipiId, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( accessToken.getUserId(), pratilipiId );
		
		UserPratilipiData userPratilipiData = createUserPratilipiData( userPratilipi, request );
		
		return userPratilipiData;
	}
	
	public static UserPratilipiData createUserPratilipiData( UserPratilipi userPratilipi, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		User user = dataAccessor.getUser( userPratilipi.getUserId() );
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setUserId( user.getId() );
		userPratilipiData.setUserName( user.getFirstName() + " " + user.getLastName() );
		userPratilipiData.setPratilipiId( userPratilipi.getPratilipiId() );
		userPratilipiData.setRating( userPratilipi.getRating() );
		userPratilipiData.setReview( userPratilipi.getReview() );
		userPratilipiData.setReviewState( userPratilipi.getReviewState() );
		userPratilipiData.setReviewDate( userPratilipi.getReviewDate() );
		userPratilipiData.setBookmarks( userPratilipi.getBookmarks() );
		
		return userPratilipiData;
	}
	
	
	public static UserPratilipiData saveUserPratilipi( UserPratilipiData userPratilipiData, HttpServletRequest request )
			throws InsufficientAccessException, InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken = ( AccessToken ) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( accessToken.getUserId(), userPratilipiData.getPratilipiId() );
		
		if( userPratilipi == null ) {	//new record
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId( accessToken.getUserId() );
			userPratilipi.setPratilipiId( userPratilipiData.getPratilipiId() );
		}
		
		if( userPratilipiData.hasBookmarks() ) {
			if( !hasRequestAccessToAddBookmarks( request ))
				throw new InsufficientAccessException();
			
			if( userPratilipiData.getBookmarkRequestType() == null )
				throw new InvalidArgumentException( "Bookmark request type is not specified" );
			
			String bookmarkString = userPratilipi.getBookmarks();
			JsonArray bookmarkGson = userPratilipi.getBookmarks() != null
					? gson.fromJson( bookmarkString, JsonElement.class ).getAsJsonArray()
					: new JsonArray();
			
			if( userPratilipiData.getBookmarkRequestType() == BookmarkRequestType.ADD ){
				for( int i = 0; i < bookmarkGson.size(); i++ ){
					String bookmark = bookmarkGson.get( i ).toString();
					if( bookmark.contains( userPratilipiData.getBookmarks() ))
							return createUserPratilipiData( userPratilipi, request );
				}
				
				JsonObject newBookmark = new JsonObject();
				newBookmark.addProperty( "title", "Page " + userPratilipiData.getBookmarks() );
				newBookmark.addProperty( "pageNo", userPratilipiData.getBookmarks() );
				bookmarkGson.add( newBookmark );
			} else if( userPratilipiData.getBookmarkRequestType() == BookmarkRequestType.REMOVE ){
				JsonArray newBookmarkArray = new JsonArray();
				for( int i = 0; i < bookmarkGson.size(); i++ ){
					String bookmark = bookmarkGson.get( i ).toString();
					if( !bookmark.contains( userPratilipiData.getBookmarks() ))
						newBookmarkArray.add( bookmarkGson.get( i ).getAsJsonObject() );
				}
				bookmarkGson = newBookmarkArray;
			}
			
			userPratilipi.setBookmarks( bookmarkGson.toString() );
		}
		
		userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
		
		return createUserPratilipiData( userPratilipi, request );
	}
	
}
