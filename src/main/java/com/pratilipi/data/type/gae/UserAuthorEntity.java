package com.pratilipi.data.type.gae;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.pratilipi.data.type.UserAuthor;

@Cache
@Entity( name = "USER_AUTHOR" )
public class UserAuthorEntity extends GenericOfyEntity implements UserAuthor {

	// Intentionally Leaving all fields UnIndexed

	@Id
	private String USER_AUTHOR_ID;
	
	private Long USER_ID;
	private Long AUTHOR_ID;

	private Boolean FOLLOWING;
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
	
	@Override
	public <T extends GenericOfyEntity> void setKey( Key<T> key ) {
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
	}

	public Date getFollowingSince() {
		return FOLLOWING_SINCE;
	}
	
	public void setFollowingSince( Date followingSince ) {
		this.FOLLOWING_SINCE = followingSince;
	}
	
}
