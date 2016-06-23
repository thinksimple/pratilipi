package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserAuthorData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.filter.AccessTokenFilter;

public class UserAuthorDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( UserAuthorDataUtil.class.getName() );

	
	public static boolean hasAccessToUpdateUserAuthorData( UserAuthor userAuthor, AccessType accessType ) {
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		
		if( ! userAuthor.getUserId().equals( accessToken.getUserId() ) )
			return false;
		
		if( ! UserAccessUtil.hasUserAccess( accessToken.getUserId(), null, accessType ) )
			return false;

		// User can not follow his/her own Author profile.
		if( accessType == AccessType.USER_AUTHOR_FOLLOWING ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			Author author = dataAccessor.getAuthor( userAuthor.getAuthorId() );
			if( userAuthor.getUserId().equals( author.getUserId() ) )
				return false;
		}
		
		return true;

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

	
	public static UserAuthorData saveUserAuthorFollow( Long userId, Long authorId, Boolean following )
			throws InvalidArgumentException, InsufficientAccessException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserAuthor userAuthor = dataAccessor.getUserAuthor( userId, authorId );
		if( userAuthor == null ) {
			userAuthor = dataAccessor.newUserAuthor();
			userAuthor.setUserId( userId );
			userAuthor.setAuthorId( authorId );
		}
		
		
		if( ! hasAccessToUpdateUserAuthorData( userAuthor, AccessType.USER_AUTHOR_FOLLOWING ) )
			throw new InsufficientAccessException();
		
		
		Gson gson = new Gson();

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.USER_AUTHOR_FOLLOWING );
		auditLog.setEventDataOld( gson.toJson( userAuthor ) );

		userAuthor.setFollowing( following );
		userAuthor.setFollowingSince( new Date() );
		
		auditLog.setEventDataNew( gson.toJson( userAuthor ) );
		
		
		userAuthor = dataAccessor.createOrUpdateUserAuthor( userAuthor, auditLog );
		
		
		return createUserAuthorData( userAuthor );
		
	}
	
}
