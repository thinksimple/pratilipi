package com.pratilipi.api.impl.comment;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.comment.shared.PostCommentRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.client.CommentData;
import com.pratilipi.data.util.CommentDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/comment" )
public class CommentApi extends GenericApi {
	
	@Post
	public GenericResponse post( PostCommentRequest request )
			throws InvalidArgumentException {
		
		CommentData commentData = new CommentData( request.getCommentId() );
		commentData.setUserId( AccessTokenFilter.getAccessToken().getUserId() );
		commentData.setParentType( request.getParentType() );
		commentData.setParentId( request.getParentId() );
		commentData.setContent( request.getContent() );
		
		commentData = CommentDataUtil.saveCommentData( commentData );
		
		return new GenericResponse();
		
	}
	
}
