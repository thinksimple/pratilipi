package com.pratilipi.data.client;

import java.util.Date;

import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.data.type.User;

public class BlogPostData {
	
	private Long blogPostId;
	
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
	
	private User createdBy;
	
	private Long creationDateMillis;
	private Long lasUpdatedMillis;
	
	
	private String pageUrl;
	
	private Boolean hasAccessToUpdate;

	
	
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
	
	public User getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy( User user ) {
		this.createdBy = user;
	}
	
	public Date getCreationDate() {
		return creationDateMillis == null ? null : new Date( creationDateMillis );
	}
	
	public void setCrationDate( Date creationDate ) {
		this.creationDateMillis = creationDate == null ? null : creationDate.getTime();
	}
	
	public Date getLastUpdated() {
		return lasUpdatedMillis == null ? null : new Date( lasUpdatedMillis );
	}
	
	public void setLastUpdated( Date lastUpdated ) {
		this.lasUpdatedMillis = lastUpdated == null ? null : lastUpdated.getTime();
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
