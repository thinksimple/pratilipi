package com.pratilipi.data.client;

import java.util.Date;

import com.pratilipi.common.type.CommentParentType;

public class CommentData {

	private Long commentId;
	
	
	private Long userId;
	
	private CommentParentType parentType;
	
	private String parentId;

	
	private String content;
	
	private Long likeCount;
	
	
	private Long creationDateMillis;

	private Long lastUpdatedMillis;

	

	public CommentData() {}

	public CommentData( Long id ) {
		this.commentId = id;
	}

	
	public Long getId() {
		return commentId;
	}
	
	public void setId( Long id ) {
		this.commentId = id;
	}
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	public CommentParentType getParentType() {
		return parentType;
	}

	public void setParentType( CommentParentType parentType ) {
		this.parentType = parentType;
	}

	public String getParentId() {
		return parentId;
	}

	public Long getParentIdLong() {
		return Long.parseLong( parentId );
	}

	public void setParentId( Long parentId ) {
		this.parentId = parentId.toString();
	}

	public void setParentId( String parentId ) {
		this.parentId = parentId;
	}

	
	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
	}
	
	public Long getLikeCount() {
		return likeCount;
	}
	
	public void setLikeCount( Long likeCount ) {
		this.likeCount = likeCount;
	}
	
	
	public Date getCreationDate() {
		return creationDateMillis == null ? null : new Date( creationDateMillis );
	}
	
	public void setCreationDate( Date creationDate ) {
		this.creationDateMillis = creationDate == null ? null : creationDate.getTime();
	}

	public Date getLastUpdated() {
		return lastUpdatedMillis == null ? null : new Date( lastUpdatedMillis );
	}

	public void setLastUpdated( Date lastUpdated ) {
		this.lastUpdatedMillis = lastUpdated == null ? null : lastUpdated.getTime();
	}

}
