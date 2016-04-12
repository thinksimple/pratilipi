package com.pratilipi.api.impl.blogpost.shared;

import java.util.Date;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.data.client.BlogPostData;
import com.pratilipi.data.type.User;

public class GenericBlogPostResponse extends GenericResponse {
	
	private Long blogPostId;
	private Long blogId;
	
	private String title;
	private String titleEn;
	private String content;
	
	private BlogPostState state;
	private User createdBy;
	private Long creationDateMillis;
	private Long lasUpdatedMillis;
	
	private String pageUrl;
	private Boolean hasAccessToUpdate;

	
	public GenericBlogPostResponse( BlogPostData blogPostData ) {
		this.blogPostId = blogPostData.getId();
		this.blogId = blogPostData.getBlogId();
		this.title = blogPostData.getTitle();
		this.titleEn = blogPostData.getTitleEn();
		this.content = blogPostData.getContent();
		this.state = blogPostData.getState();
		this.createdBy = blogPostData.getCreatedBy();
		this.creationDateMillis = blogPostData.getCreationDate().getTime();
		this.lasUpdatedMillis = blogPostData.getLastUpdated().getTime();
		this.pageUrl = blogPostData.getPageUrl();
		this.hasAccessToUpdate = blogPostData.hasAccessToUpdate();
	}
	
	
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
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}
	
	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn( String titleEn ) {
		this.titleEn = titleEn;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent( String content ) {
		this.content = content;
	}

	
	public BlogPostState getState() {
		return state;
	}
	
	public void setBlogPostState( BlogPostState state ) {
		this.state = state;
	}
	
	public User getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy( User user ) {
		this.createdBy = user;
	}
	
	public Date getCreationDate() {
		return new Date( creationDateMillis );
	}
	
	public void setCrationDate( Date creationDate ) {
		this.creationDateMillis = creationDate.getTime();
	}
	
	public Date getLastUpdated() {
		return new Date( lasUpdatedMillis );
	}
	
	public void setLastUpdated( Date lastUpdated ) {
		this.lasUpdatedMillis = lastUpdated.getTime();
	}
	
	
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl( String pageUrl ) {
		this.pageUrl = pageUrl;
	}

	public boolean hasAccessToUpdate() {
		return hasAccessToUpdate == null ? false : hasAccessToUpdate;
	}
	
	public void setAccessToUpdate( Boolean hasAccessToUpdate ) {
		this.hasAccessToUpdate = hasAccessToUpdate;
	}
	
}
