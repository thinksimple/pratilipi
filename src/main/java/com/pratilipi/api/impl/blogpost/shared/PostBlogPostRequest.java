package com.pratilipi.api.impl.blogpost.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.BlogPostState;

public class PostBlogPostRequest extends GenericRequest {

	@Validate( minLong = 1L )
	private Long blogPostId;
	
	@Validate( minLong = 1L )
	private Long blogId;
	private boolean hasBlogId;
	
	
	private String title;
	private boolean hasTitle;
	
	private String titleEn;
	private boolean hasTitleEn;
	
	private String content;
	private boolean hasContent;
	
	
	private BlogPostState state;
	private boolean hasState;
	
	
	
	public Long getId() {
		return blogPostId;
	}

	public void setId( Long id ) {
		this.blogPostId = id;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId( Long blogId ) {
		this.blogId = blogId;
		this.hasBlogId = true;
	}

	public boolean hasBlogId() {
		return hasBlogId;
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
		this.hasTitle = true;
	}
	
	public boolean hasTitle() {
		return hasTitle;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn( String titleEn ) {
		this.titleEn = titleEn;
		this.hasTitleEn = true;
	}
	
	public boolean hasTitleEn() {
		return hasTitleEn;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent( String content ) {
		this.content = content;
		this.hasContent = true;
	}

	public boolean hasContent() {
		return hasContent;
	}

	
	public BlogPostState getState() {
		return state;
	}
	
	public void setBlogPostState( BlogPostState state ) {
		this.state = state;
		this.hasState = true;
	}
	
	public boolean hasState() {
		return hasState;
	}
	
}
