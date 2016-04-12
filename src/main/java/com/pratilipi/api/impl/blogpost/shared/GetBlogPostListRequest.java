package com.pratilipi.api.impl.blogpost.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetBlogPostListRequest extends GenericRequest {

	private Long blogId;

	private String cursor;
	private Integer resultCount;
	
	
	public Long getBlogId() {
		return blogId;
	}
	
	public String getCursor() {
		return cursor;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}
	
}
