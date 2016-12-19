package com.pratilipi.data.client;

import java.util.Date;

import com.pratilipi.common.type.UserFollowState;

public class UserAuthorData {

	private String userAuthorId;
	
	private Long userId;
	
	private Long authorId;
	
	private Boolean following;
	private boolean hasFollowing;

	private UserFollowState state;
	private Long followingSinceMillis;

	
	public String getId() {
		return userAuthorId;
	}
	
	public void setId( String id ) {
		this.userAuthorId = id;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId( Long userId ) {
		this.userId = userId;
	}
	
	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}

	
	public Boolean isFollowing() {
		return following;
	}

	public void setFollowing( Boolean following ) {
		this.following = following;
		this.hasFollowing = true;
	}
	
	public boolean hasFollowing() {
		return hasFollowing;
	}

	public UserFollowState getState() {
		return state;
	}

	public void setState( UserFollowState state ) {
		this.state = state;
	}

	public Date getFollowingSince() {
		return followingSinceMillis == null ? null : new Date( followingSinceMillis );
	}

	public void setFollowingSince( Date followingSince ) {
		followingSinceMillis = followingSince == null ? null : followingSince.getTime();
	}

}
