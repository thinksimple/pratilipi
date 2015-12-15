package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.filter.AccessTokenFilter;

public class UserPratilipiDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( UserPratilipiDataUtil.class.getName() );

	
	public static boolean hasAccessToAddUserPratilipiData( Long pratilipiId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( ! UserAccessUtil.hasUserAccess( accessToken.getUserId(), null, AccessType.PRATILIPI_ADD_REVIEW ) )
			return false;
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
			return false;
		
		return true;
	}
	

	public static UserPratilipiData createUserPratilipiData( UserPratilipi userPratilipi ) {
		if( userPratilipi == null )
			return null;
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setPratilipiId( userPratilipi.getPratilipiId() );
		userPratilipiData.setRating( userPratilipi.getRating() );
		userPratilipiData.setReviewTitle( userPratilipi.getReviewTitle() );
		userPratilipiData.setReview( userPratilipi.getReview() );
		userPratilipiData.setReviewDate( userPratilipi.getReviewDate() );
		return userPratilipiData;
	}
	
	public static List<UserPratilipiData> createPratilipiReviewDataList( List<UserPratilipi> userPratilipiList ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<UserPratilipiData> userPratilipiDataList = new ArrayList<>( userPratilipiList.size() );
		for( UserPratilipi userPratilipi : userPratilipiList ) {
			User user = dataAccessor.getUser( userPratilipi.getUserId() );

			UserPratilipiData userPratilipiData = new UserPratilipiData();
			userPratilipiData.setUserName( UserDataUtil.createUserName( user ) );
			userPratilipiData.setPratilipiId( userPratilipi.getPratilipiId() );
			userPratilipiData.setRating( userPratilipi.getRating() );
			userPratilipiData.setReviewTitle( userPratilipi.getReviewTitle() );
			userPratilipiData.setReview( userPratilipi.getReview() == null ? null : userPratilipi.getReview().replaceAll( "<[^>]*>", "" ) );
			userPratilipiData.setReviewDate( userPratilipi.getReviewDate() );
			
			userPratilipiDataList.add( userPratilipiData );
		}
		
		return userPratilipiDataList;
	}

	
	public static UserPratilipiData getUserPratilipi( Long pratilipiId ) {
		Long userId = AccessTokenFilter.getAccessToken().getUserId();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( userId, pratilipiId );
		if( userPratilipi == null && ! userId.equals( 0L ) ) {
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId( userId );
			userPratilipi.setPratilipiId( pratilipiId );
		}
		return createUserPratilipiData( userPratilipi );
	}
	
	public static DataListCursorTuple<UserPratilipiData> getPratilipiReviewList(
			Long pratilipiId, String cursor, Integer resultCount ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		DataListCursorTuple<UserPratilipi> userPratilipiListCursorTuple =
				dataAccessor.getPratilipiReviewList( pratilipiId, cursor, resultCount );
		List<UserPratilipi> userPratilipiList = userPratilipiListCursorTuple.getDataList();
		
		return new DataListCursorTuple<UserPratilipiData>(
				createPratilipiReviewDataList( userPratilipiList ),
				userPratilipiListCursorTuple.getCursor() );
	}
	
	public static void saveUserPratilipi( UserPratilipiData userPratilipiData )
			throws InsufficientAccessException {

		if( ! hasAccessToAddUserPratilipiData( userPratilipiData.getPratilipiId() ) )
			throw new InsufficientAccessException();

		if( userPratilipiData.getRating() == null && userPratilipiData.getReview() == null )
			return;

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi(
				AccessTokenFilter.getAccessToken().getUserId(),
				userPratilipiData.getPratilipiId() );

		if( userPratilipi == null ) {
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId( AccessTokenFilter.getAccessToken().getUserId() );
			userPratilipi.setPratilipiId( userPratilipiData.getPratilipiId() );
		}
		
		if( userPratilipiData.getRating() != null ) {
			userPratilipi.setRating( userPratilipiData.getRating() );
			userPratilipi.setRatingDate( new Date() );
		}
		
		if( userPratilipiData.getReviewTitle() != null )
			userPratilipi.setReviewTitle( userPratilipiData.getReviewTitle() );

		if( userPratilipiData.getReview() != null ) {
			userPratilipi.setReview( userPratilipiData.getReview() );
			userPratilipi.setReviewDate( new Date() );
		}
		
		userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
	}
	
}
