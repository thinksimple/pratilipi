package com.pratilipi.api.impl.blogpost.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.common.type.Language;

public class GetBlogPostListRequest extends GenericRequest {

	@Validate( required = true )
	private Long blogId;
	private Language language;
	private BlogPostState state;
	
	private String cursor;
	private Integer resultCount;
	
	
	public Long getBlogId() {
		return blogId;
	}
	
	public Language getLanguage() {
		return language;
	}

	public BlogPostState getState() {
		return state;
	}

	
	public String getCursor() {
		return cursor;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}
	
}
