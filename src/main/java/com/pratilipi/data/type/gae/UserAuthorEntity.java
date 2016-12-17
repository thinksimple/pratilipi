package com.pratilipi.data.type.gae;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.pratilipi.common.type.UserFollowState;
import com.pratilipi.data.type.UserAuthor;

@Cache
@Entity( name = "USER_AUTHOR" )
public class UserAuthorEntity implements UserAuthor {

	@Id
	private String USER_AUTHOR_ID;

	@Index
	private Long USER_ID;
	@Index
	private Long AUTHOR_ID;

	@Index
	private Boolean FOLLOWING;
	@Index
	private UserFollowState STATE;
	@Index
	private Date FOLLOWING_SINCE;
	
	
	public UserAuthorEntity() {}
	
	public UserAuthorEntity( Long userId, Long authorId ) {
		this.USER_AUTHOR_ID = userId + "-" + authorId;
		this.USER_ID = userId;
		this.AUTHOR_ID = authorId;
	}

	
	@Override
	public String getId() {
		return this.USER_AUTHOR_ID;
	}
	
	public void setId( String id ) {
		this.USER_AUTHOR_ID = id;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Key<T> getKey() {
		return getId() == null ? null : (Key<T>) Key.create( getClass(), getId() );
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.USER_AUTHOR_ID = key.getName();
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
	public Long getAuthorId() {
		return AUTHOR_ID;
	}
	
	@Override
	public void setAuthorId( Long authorId ) {
		this.AUTHOR_ID = authorId;
	}
	
	
	public Boolean isFollowing() {
		return FOLLOWING == null ? false : FOLLOWING;
	}
	
	public void setFollowing( Boolean isFollowing ) {
		this.FOLLOWING = isFollowing;
		// TODO: Remove it after backfilling
		this.STATE = isFollowing ? UserFollowState.FOLLOWING : UserFollowState.UNFOLLOWED;
	}

	public UserFollowState getState() {
		return STATE;
	}

	public void setState( UserFollowState state ) {
		this.STATE = state;
	}

	public Date getFollowingSince() {
		return FOLLOWING_SINCE;
	}
	
	public void setFollowingSince( Date followingSince ) {
		this.FOLLOWING_SINCE = followingSince;
	}
	
}
