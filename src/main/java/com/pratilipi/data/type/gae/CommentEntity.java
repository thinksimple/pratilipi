package com.pratilipi.data.type.gae;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.pratilipi.data.type.Comment;

@Cache
@Entity( name = "COMMENT" )
public class CommentEntity implements Comment {

	@Id
	private Long COMMENT_ID;
	
	
	@Index
	private Long USER_ID;
	
	@Index
	private String CONTENT_ID;

	@Deprecated
	private String PARENT_TYPE;
	
	@Deprecated
	private String PARENT_ID;

	
	@Deprecated
	private String CONTENT;
	
	private String COMMENT_TEXT;
	
	
	@Deprecated
	private Long UPVOTE;
	
	@Deprecated
	private Long DOWNVOTE;
	
	
	@Deprecated
	private Date COMMENT_DATE;
	
	@Deprecated
	private Date COMMENT_LAST_UPDATED_DATE;			
	
	
	@Index
	private Date CREATION_DATE;

	@Index
	private Date LAST_UPDATED;

	

	public CommentEntity() {}

	public CommentEntity( Long id ) {
		this.COMMENT_ID = id;
	}

	
	@Override
	public Long getId() {
		return COMMENT_ID;
	}
	
	public void setId( Long id ) {
		this.COMMENT_ID = id;
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.COMMENT_ID = key.getId();
	}
	
	
	@Override
	public Long getUserId() {
		return USER_ID;
	}

	@Override
	public void setUserId( Long userId ) {
		this.USER_ID = userId;
	}

	@Override
	public String getContentId() {
		if( CONTENT_ID == null )
			CONTENT_ID = PARENT_TYPE + "::" + PARENT_ID;
		return CONTENT_ID;
	}

	@Override
	public void setContentId( String contentId ) {
		this.CONTENT_ID = contentId;
	}

	
	@Override
	public String getCommentText() {
		if( COMMENT_TEXT == null )
			COMMENT_TEXT = CONTENT;
		return COMMENT_TEXT;
	}

	@Override
	public void setCommentText( String commentText ) {
		this.COMMENT_TEXT = commentText;
	}
	
	
	@Override
	public Date getCreationDate() {
		if( COMMENT_DATE != null ) {
			CREATION_DATE = COMMENT_DATE;
			COMMENT_DATE = null;
		}
		return CREATION_DATE;
	}
	
	@Override
	public void setCreationDate( Date creationDate ) {
		this.CREATION_DATE = creationDate;
	}

	@Override
	public Date getLastUpdated() {
		if( COMMENT_LAST_UPDATED_DATE != null ) {
			LAST_UPDATED = COMMENT_LAST_UPDATED_DATE;
			COMMENT_LAST_UPDATED_DATE = null;
		}
		return LAST_UPDATED;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.LAST_UPDATED = lastUpdated;
	}

}
