package com.pratilipi.api.impl.comment;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.CommentParentType;
import com.pratilipi.common.type.CommentState;
import com.pratilipi.data.client.CommentData;
import com.pratilipi.data.util.CommentDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/comment" )
public class CommentApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {
		
		@Validate( minLong = 1L )
		private Long commentId;
		
		private CommentParentType parentType;
		
		private String parentId;

		private String content;
		private boolean hasContent;
		
		private CommentState state;
		private boolean hasState;
		
	}
	
	@Post
	public GenericResponse post( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		CommentData commentData = new CommentData( request.commentId );
		
		commentData.setUserId( AccessTokenFilter.getAccessToken().getUserId() );
		
		commentData.setParentType( request.parentType );
		commentData.setParentId( request.parentId );
		
		if( request.hasContent )
			commentData.setContent( request.content );
		
		if( request.hasState )
			commentData.setState( request.state );
		else
			commentData.setState( CommentState.ACTIVE );
		
		commentData = CommentDataUtil.saveCommentData( commentData );
		
		return new GenericResponse();
		
	}
	
}
