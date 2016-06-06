package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.CommentParentType;
import com.pratilipi.common.type.CommentState;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.client.CommentData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Comment;
import com.pratilipi.data.type.CommentDoc;
import com.pratilipi.data.type.PratilipiReviewsDoc;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserPratilipiDoc;
import com.pratilipi.filter.AccessTokenFilter;

public class CommentDataUtil {
	
	public static boolean hasAccessToAddCommentData( CommentData commentData ) {
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( ! UserAccessUtil.hasUserAccess( accessToken.getUserId(), null, AccessType.COMMENT_ADD ) )
			return false;
		
		return true;
		
	}
	
	public static boolean hasAccessToUpdateCommentData( Comment comment, CommentData commentData ) {
		return hasAccessToUpdateCommentData( comment.getState(), comment.getUserId() );
	}

	public static boolean hasAccessToUpdateCommentData( CommentState commentState, Long commentUserId ) {
		
		if( commentState != CommentState.ACTIVE
				&& commentState != CommentState.DELETED )
			return false;
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		return commentUserId.equals( accessToken.getUserId() );
	
	}

	
	public static CommentData createCommentData( Comment comment ) {
		
		CommentData commentData = new CommentData( comment.getId() );
		
		commentData.setUserId( comment.getUserId() );
		commentData.setContent( comment.getContent() );
		commentData.setState( comment.getState() );
		commentData.setCreationDate( comment.getCreationDate() );
		commentData.setLastUpdated( comment.getLastUpdated() );

		commentData.setAccessToUpdate( hasAccessToUpdateCommentData(
				comment,
				commentData ) );

		return commentData;
		
	}

	public static CommentData createCommentData( CommentDoc commentDoc ) {
		
		CommentData commentData = new CommentData( commentDoc.getId() );
		
		commentData.setUserId( commentDoc.getUserId() );
		commentData.setContent( commentDoc.getContent() );
		commentData.setState( CommentState.ACTIVE );
		commentData.setCreationDate( commentDoc.getCreationDate() );
		commentData.setLastUpdated( commentDoc.getLastUpdated() );
		
		commentData.setAccessToUpdate( hasAccessToUpdateCommentData(
				CommentState.ACTIVE,
				commentDoc.getUserId() ) );
		
		return commentData;
		
	}
	
	public static List<CommentData> createCommentDataList( List<CommentDoc> commentDocList ) {
		
		List<Long> userIdList = new ArrayList<>( commentDocList.size() );
		for( CommentDoc commentDoc : commentDocList )
			if( ! userIdList.contains( commentDoc.getUserId() ) )
				userIdList.add( commentDoc.getUserId() );
		
		Map<Long, UserData> userDataMap = UserDataUtil.createUserDataList( userIdList );
		
		List<CommentData> commentDataList = new ArrayList<>( commentDocList.size() );
		for( CommentDoc commentDoc : commentDocList ) {
			CommentData commentData = createCommentData( commentDoc );
			commentData.setUser( userDataMap.get( commentDoc.getUserId() ) );
			commentDataList.add( commentData );
		}
		
		return commentDataList;
		
	}
	
	
	public static DataListCursorTuple<CommentData> getCommentList(
			CommentParentType parentType, String parentId,
			String cursor, Integer offset, Integer resultCount ) throws UnexpectedServerException {

		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		
		if( parentType == CommentParentType.REVIEW ) {
			
			Long userId = Long.parseLong( parentId.substring( 0, parentId.indexOf( '-' ) ) );
			Long pratilipiId = Long.parseLong( parentId.substring( parentId.indexOf( '-' ) + 1 ) );
			
			PratilipiReviewsDoc reviewsDoc = docAccessor.getPratilipiReviewsDoc( pratilipiId );
			List<CommentDoc> commentDocList = null;
			for( UserPratilipiDoc reviewDoc : reviewsDoc.getReviews() ) {
				if( reviewDoc.getUserId().equals( userId ) ) {
					commentDocList = reviewDoc.getComments();
					break;
				}
			}

			int fromIndex = ( cursor == null ? 0 : Integer.parseInt( cursor ) ) + ( offset == null ? 0 : offset );
			int toIndex = resultCount == null ? commentDocList.size() : offset + resultCount;
			
			commentDocList = commentDocList.subList(
					Math.min( fromIndex, commentDocList.size() ),
					Math.min( toIndex, commentDocList.size() ) );
			
			List<CommentData> commentDataList = createCommentDataList( commentDocList );
			
			return new DataListCursorTuple<CommentData>(
					commentDataList,
					toIndex + "",
					(long) (int) reviewsDoc.getReviews().size() );
			
		}
		
		return null;
		
	}
	
	public static CommentData saveCommentData( CommentData commentData )
			throws InvalidArgumentException, InsufficientAccessException {
		
		_validateCommentDataForSave( commentData );
		
		boolean isNew = commentData.getId() == null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Comment comment = isNew ? dataAccessor.newComment() : dataAccessor.getComment( commentData.getId() );

		if ( isNew && ! hasAccessToAddCommentData( commentData ) )
			throw new InsufficientAccessException();
		if( ! isNew && ! hasAccessToUpdateCommentData( comment, commentData ) )
			throw new InsufficientAccessException();

		
		Gson gson = new Gson();

		
		AuditLog auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );
		auditLog.setAccessType( isNew ? AccessType.COMMENT_ADD : AccessType.COMMENT_UPDATE );
		auditLog.setEventDataOld( gson.toJson( comment ) );

		
		if( isNew ) {
			comment.setUserId( commentData.getUserId() );
			comment.setParentType( commentData.getParentType() );
			comment.setParentId( commentData.getParentId() );
			comment.setCreationDate( new Date() );
		}

		if( commentData.hasContent() )
			comment.setContent( commentData.getContent() );
		if( commentData.hasState() )
			comment.setState( commentData.getState() );
		comment.setLastUpdated( new Date() );
		
		
		auditLog.setEventDataNew( gson.toJson( comment ) );
		
		
		comment = dataAccessor.createOrUpdateComment( comment, auditLog );

		
		User user = dataAccessor.getUser( commentData.getUserId() );
		UserData userData = UserDataUtil.createUserData( user );
		
		commentData = createCommentData( comment );
		commentData.setUser( userData );
		
		return commentData;
		
	}
	
	private static void _validateCommentDataForSave( CommentData commentData )
			throws InvalidArgumentException {
		
		boolean isNew = commentData.getId() == null;
		
		JsonObject errorMessages = new JsonObject();

		// New comment must have a user id.
		if( isNew && ( commentData.getUserId() == null || commentData.getUserId() == 0L ) )
			errorMessages.addProperty( "userId", GenericRequest.ERR_USER_ID_REQUIRED );
		
		// New comment must have a parent type.
		if( isNew && commentData.getParentType() == null )
			errorMessages.addProperty( "parentType", GenericRequest.ERR_COMMENT_PARENT_TYPE_REQUIRED );
		
		// New comment must have a parent id.
		if( isNew && commentData.getParentId() == null )
			errorMessages.addProperty( "parentId", GenericRequest.ERR_COMMENT_PARENT_ID_REQUIRED );
		
		// Old/New comment must have content.
		if( commentData.getContent() == null )
			errorMessages.addProperty( "content", GenericRequest.ERR_COMMENT_CONTENT_REQUIRED );
		
		if( errorMessages.entrySet().size() > 0 )
			throw new InvalidArgumentException( errorMessages );

	}
	
}