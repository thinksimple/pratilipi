package com.pratilipi.api.impl.comment.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.CommentParentType;


public class PostCommentRequest extends GenericRequest {
	
	@Validate( minLong = 1L )
	private Long commentId;
	
	private CommentParentType parentType;
	
	private String parentId;

	private String content;
	

	public Long getCommentId() {
		return commentId;
	}
	
	public CommentParentType getParentType() {
		return parentType;
	}

	public String getParentId() {
		return parentId;
	}

	public String getContent() {
		return content;
	}

}
