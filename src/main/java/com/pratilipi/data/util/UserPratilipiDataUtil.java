package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.common.util.UserPratilipiFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.filter.AccessTokenFilter;

public class UserPratilipiDataUtil {
	
	public static boolean hasAccessToAddUserPratilipiData( Long pratilipiId ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Case 1: User not having PRATILIPI_ADD_REVIEW access can not review a content piece.
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( ! UserAccessUtil.hasUserAccess( accessToken.getUserId(), null, AccessType.PRATILIPI_ADD_REVIEW ) )
			return false;

		// Case 2: User can not review content pieces lined with his/her own author profile.
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = pratilipi.getAuthorId() == null ? null : dataAccessor.getAuthor( pratilipi.getAuthorId() );
		if( author != null && accessToken.getUserId().equals( author.getUserId() ) )
			return false;
		
		return true;
		
	}
	

	public static UserPratilipiData createUserPratilipiData( UserPratilipi userPratilipi ) {
		
		if( userPratilipi == null )
			return null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( userPratilipi.getUserId() );
		UserData userData = UserDataUtil.createUserData( user );
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setId( userPratilipi.getId() );
		userPratilipiData.setUserId( userPratilipi.getUserId() );
		userPratilipiData.setUserName( userData.getDisplayName() );
		userPratilipiData.setUserImageUrl( userData.getProfileImageUrl() );
		userPratilipiData.setUserProfilePageUrl( userData.getProfilePageUrl() );
		userPratilipiData.setPratilipiId( userPratilipi.getPratilipiId() );
		userPratilipiData.setRating( userPratilipi.getRating() );
		userPratilipiData.setReviewTitle( userPratilipi.getReviewTitle() );
		userPratilipiData.setReview( userPratilipi.getReview() == null ? null : userPratilipi.getReview().replaceAll( "<[^>]*>", "" ) );
		userPratilipiData.setReviewState( userPratilipi.getReviewState() );
		userPratilipiData.setReviewDate( userPratilipi.getReviewDate() );
		userPratilipiData.setAccessToReview( hasAccessToAddUserPratilipiData( userPratilipi.getPratilipiId() ) );
		userPratilipiData.setAddedToLib( userPratilipi.isAddedToLib() );
		
		return userPratilipiData;
		
	}
	
	public static List<UserPratilipiData> createUserPratilipiDataList( List<UserPratilipi> userPratilipiList ) {
		List<UserPratilipiData> userPratilipiDataList = new ArrayList<>( userPratilipiList.size() );
		for( UserPratilipi userPratilipi : userPratilipiList )
			userPratilipiDataList.add( createUserPratilipiData( userPratilipi ) );
		return userPratilipiDataList;
	}

	
	public static UserPratilipiData getUserPratilipi( Long pratilipiId ) {

		Long userId = AccessTokenFilter.getAccessToken().getUserId();
		if( userId.equals( 0L ) )
			return null;

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( userId, pratilipiId );
		if( userPratilipi == null ) {
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId( userId );
			userPratilipi.setPratilipiId( pratilipiId );
		}
		
		return createUserPratilipiData( userPratilipi );
		
	}
	
	public static DataListCursorTuple<PratilipiData> getUserPratilipiLibrary(
			Long userId, String cursor, Integer offset, Integer resultCount ) {
		
		UserPratilipiFilter userPratilipiFilter = new UserPratilipiFilter();
		userPratilipiFilter.setUserId( userId );
		userPratilipiFilter.setAddedToLib( true );
		userPratilipiFilter.setOrderByAddedToLibDate( false );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DataListCursorTuple<Long> pratilipiIdListCursorTuple =
				dataAccessor.getPratilipiIdList( userPratilipiFilter, cursor, offset, resultCount );
		
		return new DataListCursorTuple<PratilipiData>(
				PratilipiDataUtil.createPratilipiDataList( pratilipiIdListCursorTuple.getDataList(), true ),
				pratilipiIdListCursorTuple.getCursor() );
		
	}
	
	public static DataListCursorTuple<UserPratilipiData> getPratilipiReviewList(
			Long pratilipiId, String cursor, Integer resultCount ) {
		
		return getPratilipiReviewList( pratilipiId, cursor, null, resultCount );
		
	}
	
	public static DataListCursorTuple<UserPratilipiData> getPratilipiReviewList(
			Long pratilipiId, String cursor, Integer offset, Integer resultCount ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		DataListCursorTuple<UserPratilipi> userPratilipiListCursorTuple =
				dataAccessor.getPratilipiReviewList( pratilipiId, cursor, offset, resultCount );
		List<UserPratilipi> userPratilipiList = userPratilipiListCursorTuple.getDataList();
		
		return new DataListCursorTuple<UserPratilipiData>(
				createUserPratilipiDataList( userPratilipiList ),
				userPratilipiListCursorTuple.getCursor() );
		
	}
	
	public static UserPratilipiData saveUserPratilipi( UserPratilipiData userPratilipiData )
			throws InsufficientAccessException {

		if( ! hasAccessToAddUserPratilipiData( userPratilipiData.getPratilipiId() ) )
			throw new InsufficientAccessException();

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi(
				AccessTokenFilter.getAccessToken().getUserId(),
				userPratilipiData.getPratilipiId() );

		if( userPratilipi == null ) {
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId( AccessTokenFilter.getAccessToken().getUserId() );
			userPratilipi.setPratilipiId( userPratilipiData.getPratilipiId() );
		}
		
		if( ! userPratilipiData.hasRating() && ! userPratilipiData.hasReview() && ! userPratilipiData.hasReviewState() && ! userPratilipiData.hasAddedToLib() )
			return createUserPratilipiData( userPratilipi );

		
		if( userPratilipiData.hasRating() ) {
			userPratilipi.setRating( userPratilipiData.getRating() );
			userPratilipi.setRatingDate( new Date() );
		}
		
		if( userPratilipiData.hasReviewTitle() )
			userPratilipi.setReviewTitle( userPratilipiData.getReviewTitle() );

		if( userPratilipiData.hasReview() ) {
			userPratilipi.setReview( userPratilipiData.getReview() );
			userPratilipi.setReviewState( UserReviewState.PUBLISHED );
			userPratilipi.setReviewDate( new Date() );
		}

		if( userPratilipiData.hasReviewState() )
			userPratilipi.setReviewState( userPratilipiData.getReviewState() );

		
		if( userPratilipiData.hasAddedToLib() )
			userPratilipi.setAddedToLib( userPratilipiData.isAddedToLib() );
		
		userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
		
		return createUserPratilipiData( userPratilipi );
		
	}
	
}
