package com.pratilipi.data.type.gae;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotNull;
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

	@Deprecated
	@Index
	private Boolean FOLLOWING;
	@Deprecated
	@Index( IfNotNull.class )
	private Date FOLLOWING_SINCE;

	@Index
	private UserFollowState FOLLOW_STATE;
	@Index
	private Date FOLLOW_DATE;
	
	
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
		return FOLLOW_STATE == null
				? ( FOLLOWING == null ? false : FOLLOWING )
				: FOLLOW_STATE == UserFollowState.FOLLOWING;
	}
	
	public void setFollowing( Boolean isFollowing ) {
		this.FOLLOWING = isFollowing;
		this.FOLLOW_STATE = isFollowing ? UserFollowState.FOLLOWING : UserFollowState.UNFOLLOWED;
	}

	public Date getFollowingSince() {
		return FOLLOW_DATE == null ? FOLLOWING_SINCE : FOLLOW_DATE;
	}
	
	public void setFollowingSince( Date followingSince ) {
		this.FOLLOWING_SINCE = null;
		this.FOLLOW_DATE = followingSince;
	}
	
	
	public UserFollowState getState() {
		if( FOLLOW_STATE == null && FOLLOWING != null )
			FOLLOW_STATE = FOLLOWING ? UserFollowState.FOLLOWING : UserFollowState.UNFOLLOWED; 
		return FOLLOW_STATE;
	}

	public void setState( UserFollowState state ) {
		this.FOLLOWING = null;
		switch( state ) {
			case FOLLOWING:
				this.FOLLOWING = true; break;
			case UNFOLLOWED:
				this.FOLLOWING = false; break;
			case IGNORED:
				this.FOLLOWING = null; break;
		}
		this.FOLLOW_STATE = state;
	}
	
	public Date getFollowDate() {
		return FOLLOW_DATE == null ? FOLLOWING_SINCE : FOLLOW_DATE;
	}
	
	public void setFollowDate( Date date ) {
		this.FOLLOWING_SINCE = null;
		this.FOLLOW_DATE = date;
	}

}
