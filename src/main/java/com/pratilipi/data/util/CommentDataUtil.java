package com.pratilipi.data.util;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.CommentData;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Comment;
import com.pratilipi.filter.AccessTokenFilter;

public class CommentDataUtil {
	
	public static CommentData createCommentData( Comment comment ) {
		
		CommentData commentData = new CommentData();
		
		commentData.setParentType( comment.getParentType() );
		commentData.setParentId( comment.getParentId() );
		commentData.setContent( comment.getContent() );
		commentData.setCreationDate( comment.getCreationDate() );
		commentData.setLastUpdated( comment.getLastUpdated() );
		
		return commentData;
		
	}
	
	
	public static CommentData saveCommentData( CommentData commentData )
			throws InvalidArgumentException {
		
		_validateCommentDataForSave( commentData );
		
		boolean isNew = commentData.getId() == null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Comment comment = isNew ? dataAccessor.newComment() : dataAccessor.getComment( commentData.getId() );

		
		Gson gson = new Gson();

		
		AuditLog auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );
		auditLog.setAccessType( AccessType.COMMENT_ADD );
		auditLog.setEventDataOld( gson.toJson( comment ) );

		
		if( isNew ) {
			comment.setUserId( commentData.getUserId() );
			comment.setParentType( commentData.getParentType() );
			comment.setParentId( commentData.getParentId() );
			comment.setContent( commentData.getContent() );
			comment.setCreationDate( new Date() );
			comment.setLastUpdated( new Date() );
		} else {
			comment.setContent( commentData.getContent() );
			comment.setLastUpdated( new Date() );
		}
		
		
		auditLog.setEventDataNew( gson.toJson( comment ) );
		
		
		comment = dataAccessor.createOrUpdateComment( comment, auditLog );
		
		
		return createCommentData( comment );
		
	}
	
	private static void _validateCommentDataForSave( CommentData commentData )
			throws InvalidArgumentException {
		
		boolean isNew = commentData.getId() == null;
		
		JsonObject errorMessages = new JsonObject();

		// New comment must have a user id.
		if( isNew && commentData.getUserId() == null )
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