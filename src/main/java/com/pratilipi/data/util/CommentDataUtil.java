package com.pratilipi.data.util;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.CommentState;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.CommentData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Comment;
import com.pratilipi.filter.AccessTokenFilter;

public class CommentDataUtil {
	
	public static boolean hasAccessToAddCommentData( CommentData commentData ) {
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( ! UserAccessUtil.hasUserAccess( accessToken.getUserId(), null, AccessType.COMMENT_ADD ) )
			return false;
		
		return true;
		
	}
	
	public static boolean hasAccessToUpdateCommentData( Comment comment, CommentData commentData ) {
		
		if( comment.getState() != CommentState.ACTIVE
				&& comment.getState() != CommentState.DELETED )
			return false;
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		return comment.getUserId().equals( accessToken.getUserId() );
		
	}
	
	
	public static CommentData createCommentData( Comment comment ) {
		
		CommentData commentData = new CommentData( comment.getId() );
		
		commentData.setParentType( comment.getParentType() );
		commentData.setParentId( comment.getParentId() );
		commentData.setContent( comment.getContent() );
		commentData.setState( comment.getState() );
		commentData.setCreationDate( comment.getCreationDate() );
		commentData.setLastUpdated( comment.getLastUpdated() );
		
		return commentData;
		
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
		
		
		return createCommentData( comment );
		
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