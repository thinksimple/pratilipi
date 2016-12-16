package com.pratilipi.data.type.doc;

import java.util.Date;

import com.pratilipi.common.type.FollowState;
import com.pratilipi.data.type.UserAuthorDoc;

public class UserAuthorDocImpl implements UserAuthorDoc {

	private String id;
	private Long userId;
	private Long authorId;

	private FollowState state;
	private Long lastUpdatedMillis;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId( String userAuthorId ) {
		this.id = userAuthorId;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	@Override
	public Long getAuthorId() {
		return authorId;
	}

	@Override
	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}


	@Override
	public FollowState getState() {
		return state;
	}

	@Override
	public void setState( FollowState state ) {
		this.state = state;
	}

	@Override
	public Date getLastUpdated() {
		return new Date( this.lastUpdatedMillis );
	}

	@Override
	public void setLastUpdated( Date date ) {
		this.lastUpdatedMillis = date.getTime();
	}

}
