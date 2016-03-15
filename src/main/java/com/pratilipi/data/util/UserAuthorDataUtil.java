package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserAuthorData;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.filter.AccessTokenFilter;

public class UserAuthorDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( UserAuthorDataUtil.class.getName() );

	
	public static boolean hasAccessToAddOrUpdateUserAuthorData( UserAuthorData userAuthorData ) {
		return userAuthorData.getUserId().equals( AccessTokenFilter.getAccessToken().getUserId() );
	}
	

	public static UserAuthorData createUserAuthorData( UserAuthor userAuthor ) {
		
		if( userAuthor == null )
			return null;
		
		UserAuthorData userAuthorData = new UserAuthorData();
		userAuthorData.setId( userAuthor.getId() );
		userAuthorData.setUserId( userAuthor.getUserId() );
		userAuthorData.setAuthorId( userAuthor.getAuthorId() );
		userAuthorData.setFollowing( userAuthor.isFollowing() );
		userAuthorData.setFollowingSince( userAuthor.getFollowingSince() );
		
		return userAuthorData;
		
	}
	
	public static List<UserAuthorData> createUserAuthorDataList( List<UserAuthor> userAuthorList ) {
		List<UserAuthorData> userPratilipiDataList = new ArrayList<>( userAuthorList.size() );
		for( UserAuthor userAuthor : userAuthorList )
			userPratilipiDataList.add( createUserAuthorData( userAuthor ) );
		return userPratilipiDataList;
	}

	
	public static UserAuthorData getUserAuthor( Long userId, Long authorId ) {

		if( userId == null || userId.equals( 0L ) || authorId == null || authorId.equals( 0L ) )
			return null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserAuthor userAuthor = dataAccessor.getUserAuthor( userId, authorId );
		if( userAuthor == null ) {
			userAuthor = dataAccessor.newUserAuthor();
			userAuthor.setUserId( userId );
			userAuthor.setAuthorId( authorId );
			userAuthor.setFollowing( false );
		}
		
		return createUserAuthorData( userAuthor );
		
	}

	
	public static UserAuthorData saveUserAuthor( UserAuthorData userAuthorData )
			throws InvalidArgumentException, InsufficientAccessException {

		_validateUserAuthorDataForSave( userAuthorData );
		
		if( ! hasAccessToAddOrUpdateUserAuthorData( userAuthorData ) )
			throw new InsufficientAccessException();
		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserAuthor userAuthor = dataAccessor.getUserAuthor(
				userAuthorData.getUserId(),
				userAuthorData.getAuthorId() );

		
		if( userAuthor == null ) {
			userAuthor = dataAccessor.newUserAuthor();
			userAuthor.setUserId( userAuthorData.getUserId() );
			userAuthor.setAuthorId( userAuthorData.getAuthorId() );
		}
		
		
		if( userAuthorData.hasFollowing() ) {
			userAuthor.setFollowing( userAuthorData.isFollowing() );
			userAuthor.setFollowingSince( new Date() );
		}
		
		
		userAuthor = dataAccessor.createOrUpdateUserAuthor( userAuthor );
		
		return createUserAuthorData( userAuthor );
		
	}
	
	private static void _validateUserAuthorDataForSave( UserAuthorData userAuthorData )
			throws InvalidArgumentException {
		
		JsonObject errorMessages = new JsonObject();
		
		if( userAuthorData.getUserId() == null || userAuthorData.getUserId().equals( 0L ) )
			errorMessages.addProperty( "userId", GenericRequest.ERR_USER_ID_REQUIRED );
		
		if( userAuthorData.getAuthorId() == null || userAuthorData.getAuthorId().equals( 0L ) )
			errorMessages.addProperty( "authorId", GenericRequest.ERR_AUTHOR_ID_REQUIRED );		
		
		if( errorMessages.entrySet().size() > 0 )
			throw new InvalidArgumentException( errorMessages );

	}
	
}
