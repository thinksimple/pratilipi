package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.common.util.HtmlUtil;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiReviewsDoc;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.UserPratilipiDoc;
import com.pratilipi.filter.AccessTokenFilter;

public class UserPratilipiDataUtil {
	
	public static boolean hasAccessToUpdateUserPratilipiData( UserPratilipi userPratilipi, AccessType accessType ) {

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( ! userPratilipi.getUserId().equals( accessToken.getUserId() ) )
			return false;
		
		if( ! UserAccessUtil.hasUserAccess( accessToken.getUserId(), null, accessType ) )
			return false;

		// Review can not be created for content pieces created by the user.
		if( accessType == AccessType.USER_PRATILIPI_REVIEW ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			Pratilipi pratilipi = dataAccessor.getPratilipi( userPratilipi.getPratilipiId() );
			Author author = pratilipi.getAuthorId() == null ? null : dataAccessor.getAuthor( pratilipi.getAuthorId() );
			if( author != null && userPratilipi.getUserId().equals( author.getUserId() ) )
				return false;
		}
		
		return true;
		
	}
	
	
	private static String processReview( String reviewTitle, String review ) {
		
		if( review == null || review.trim().isEmpty() )
			return reviewTitle == null || reviewTitle.trim().isEmpty() ? null : reviewTitle.trim();
		
		review = HtmlUtil.toPlainText( review );

		return reviewTitle == null || reviewTitle.trim().isEmpty() ? review : reviewTitle + "\n\n" + review;

	}

	private static UserPratilipiData createUserPratilipiData( UserPratilipi userPratilipi )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		
		User user = dataAccessor.getUser( userPratilipi.getUserId() );
		UserData userData = UserDataUtil.createUserData( user );
		PratilipiReviewsDoc reviewsDoc = docAccessor.getPratilipiReviewsDoc( userPratilipi.getPratilipiId() );
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		
		userPratilipiData.setId( userPratilipi.getId() );
		userPratilipiData.setUserId( userPratilipi.getUserId() );
		userPratilipiData.setPratilipiId( userPratilipi.getPratilipiId() );

		userPratilipiData.setUser( userData );
		userPratilipiData.setUserName( userData.getDisplayName() );
		userPratilipiData.setUserImageUrl( userData.getProfileImageUrl() );
		userPratilipiData.setUserProfilePageUrl( userData.getProfilePageUrl() );

		userPratilipiData.setRating( userPratilipi.getRating() );
		userPratilipiData.setReview( processReview( userPratilipi.getReviewTitle(), userPratilipi.getReview() ) );
		userPratilipiData.setReviewState( userPratilipi.getReviewState() );
		userPratilipiData.setReviewDate( userPratilipi.getReviewDate() );
		
		for( UserPratilipiDoc review : reviewsDoc.getReviews() ) {
			if( review.getId().equals( userPratilipi.getId() ) ) {
				userPratilipiData.setCommentCount( review.getCommentCount() );
				userPratilipiData.setLikeCount( review.getLikeCount() );
				userPratilipiData.setLiked( review.getLikedByUserIds().contains( AccessTokenFilter.getAccessToken().getUserId() ) );
				break;
			}
		}

		userPratilipiData.setAddedToLib( userPratilipi.isAddedToLib() );
		
		userPratilipiData.setAccessToReview( hasAccessToUpdateUserPratilipiData( userPratilipi, AccessType.USER_PRATILIPI_REVIEW ) );
		
		return userPratilipiData;
		
	}
	
	private static UserPratilipiData createUserPratilipiData( UserPratilipiDoc userPratilipiDoc ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		User user = dataAccessor.getUser( userPratilipiDoc.getUserId() );
		UserData userData = UserDataUtil.createUserData( user );
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		
		userPratilipiData.setId( userPratilipiDoc.getId() );
		userPratilipiData.setUserId( userPratilipiDoc.getUserId() );
		
		userPratilipiData.setUser( userData );
		userPratilipiData.setUserName( userData.getDisplayName() );
		userPratilipiData.setUserImageUrl( userData.getProfileImageUrl() );
		userPratilipiData.setUserProfilePageUrl( userData.getProfilePageUrl() );
		
		userPratilipiData.setRating( userPratilipiDoc.getRating() );
		userPratilipiData.setReview( processReview( userPratilipiDoc.getReviewTitle(), userPratilipiDoc.getReview() ) );
		userPratilipiData.setReviewDate( userPratilipiDoc.getReviewDate() );
		
		userPratilipiData.setLikeCount( userPratilipiDoc.getLikeCount() );
		userPratilipiData.setCommentCount( userPratilipiDoc.getCommentCount() );
		
		userPratilipiData.setLiked( userPratilipiDoc.getLikedByUserIds().contains( AccessTokenFilter.getAccessToken().getUserId() ) );
		
		return userPratilipiData;
		
	}

	
	public static UserPratilipiData getUserPratilipi( Long userId, Long pratilipiId )
			throws UnexpectedServerException {

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
	
	public static DataListCursorTuple<PratilipiData> getUserLibrary( Long userId, String cursor, Integer offset, Integer resultCount ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		DataListCursorTuple<Long> pratilipiIdListCursorTuple =
				dataAccessor.getUserLibrary( userId, cursor, offset, resultCount );
		
		return new DataListCursorTuple<PratilipiData>(
				PratilipiDataUtil.createPratilipiDataList( pratilipiIdListCursorTuple.getDataList(), true ),
				pratilipiIdListCursorTuple.getCursor() );
		
	}
	
	public static DataListCursorTuple<UserPratilipiData> getPratilipiReviewList( Long pratilipiId, String cursor, Integer offset, Integer resultCount )
			throws UnexpectedServerException {
		
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		
		PratilipiReviewsDoc reviewsDoc = docAccessor.getPratilipiReviewsDoc( pratilipiId );
		List<UserPratilipiDoc> reviewDocList = reviewsDoc.getReviews();
		
		
		int fromIndex = ( cursor == null ? 0 : Integer.parseInt( cursor ) ) + ( offset == null ? 0 : offset );
		int toIndex = resultCount == null ? reviewDocList.size() : fromIndex + resultCount;
		
		reviewDocList = reviewDocList.subList(
				Math.min( fromIndex, reviewDocList.size() ),
				Math.min( toIndex, reviewDocList.size() ) );
		
		
		List<UserPratilipiData> userPratilipiDataList = new ArrayList<>( reviewDocList.size() );
		for( UserPratilipiDoc review : reviewDocList )
			userPratilipiDataList.add( createUserPratilipiData( review ) );
		
		
		return new DataListCursorTuple<UserPratilipiData>(
				userPratilipiDataList,
				toIndex + "",
				(long) (int) reviewsDoc.getReviews().size() );
		
	}
	
	public static UserPratilipiData saveUserPratilipiAddToLibrary( Long userId, Long pratilipiId, Boolean addedToLibrary )
			throws InsufficientAccessException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( userId, pratilipiId );
		if( userPratilipi == null ) {
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId( userId );
			userPratilipi.setPratilipiId( pratilipiId );
		}

		
		if( ! hasAccessToUpdateUserPratilipiData( userPratilipi, AccessType.USER_PRATILIPI_ADDED_TO_LIB ) )
			throw new InsufficientAccessException();

		
		AuditLog auditLog = dataAccessor.newAuditLog(
				AccessTokenFilter.getAccessToken(),
				AccessType.USER_PRATILIPI_ADDED_TO_LIB,
				userPratilipi );

		userPratilipi.setAddedToLib( addedToLibrary );
		userPratilipi.setAddedToLibDate( new Date() );

		userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi, auditLog );
		
		
		return createUserPratilipiData( userPratilipi );
		
	}
	
	public static UserPratilipiData saveUserPratilipiReview( Long userId, Long pratilipiId, Integer rating, String review, UserReviewState reviewState )
			throws InsufficientAccessException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( userId, pratilipiId );
		if( userPratilipi == null ) {
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId( userId );
			userPratilipi.setPratilipiId( pratilipiId );
		}
		

		if( ! hasAccessToUpdateUserPratilipiData( userPratilipi, AccessType.USER_PRATILIPI_REVIEW ) )
			throw new InsufficientAccessException();

		
		AuditLog auditLog = dataAccessor.newAuditLog(
				AccessTokenFilter.getAccessToken(),
				AccessType.USER_PRATILIPI_REVIEW,
				userPratilipi );
		
		
		if( rating != null
				&& ! rating.equals( userPratilipi.getRating() )
				&& !( rating == 0 && userPratilipi.getRating() == null ) ) {
			userPratilipi.setRating( rating );
			userPratilipi.setRatingDate( new Date() );
		}
		
		if( review != null ) {
			userPratilipi.setReviewTitle( null );
			userPratilipi.setReview( review );
			if( userPratilipi.getReviewState() != UserReviewState.PUBLISHED ) {
				// NOTE: As review comment and likes are counted only if they
				// were created after review date, do NOT update reviewDate if
				// review is already published.
				userPratilipi.setReviewState( UserReviewState.PUBLISHED );
				userPratilipi.setReviewDate( new Date() );
			}
		}

		// NOTE: As review comment and likes are counted only if they
		// were created after review date, do NOT update reviewDate if
		// review is already published.
		if( reviewState != null && reviewState != userPratilipi.getReviewState() ) {
			userPratilipi.setReviewState( reviewState );
			userPratilipi.setReviewDate( new Date() );
		}

		
		userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi, auditLog );

		
		return createUserPratilipiData( userPratilipi );
		
	}
	
}
