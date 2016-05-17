package com.pratilipi.data.type.gae;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.pratilipi.common.type.VoteType;
import com.pratilipi.data.type.UserVote;

@Cache
@Entity( name = "USER_VOTE" )
public class UserVoteEntity implements UserVote {

	@Id
	private String VOTE_ID;
	
	
	@Index
	private Long USER_ID;
	
	@Index
	private String CONTENT_ID;
	
	
	@Index
	private VoteType VOTE;
	
	
	@Index
	private Date CREATION_DATE;

	@Index
	private Date LAST_UPDATED;

	

	public UserVoteEntity() {}

	public UserVoteEntity( String id ) {
		this.VOTE_ID = id;
	}

	
	@Override
	public String getId() {
		return VOTE_ID;
	}
	
	public void setId( String id ) {
		this.VOTE_ID = id;
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.VOTE_ID = key.getName();
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
		return CONTENT_ID;
	}
	
	@Override
	public void setContentId( String contentId ) {
		this.CONTENT_ID = contentId;
	}

	
	@Override
	public VoteType getVote() {
		return VOTE;
	}
	
	@Override
	public void setVote( VoteType vote ) {
		this.VOTE = vote;
	}
	
	
	@Override
	public Date getCreationDate() {
		return CREATION_DATE;
	}
	
	@Override
	public void setCreationDate( Date creationDate ) {
		this.CREATION_DATE = creationDate;
	}

	@Override
	public Date getLastUpdated() {
		return LAST_UPDATED;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.LAST_UPDATED = lastUpdated;
	}

}
