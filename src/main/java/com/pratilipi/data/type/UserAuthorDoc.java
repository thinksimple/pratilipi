package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.FollowState;

public interface UserAuthorDoc {

	String getId();

	void setId( String userAuthorId );

	Long getUserId();

	void setUserId( Long userId );

	Long getAuthorId();

	void setUserId();


	FollowState getState();

	Date getLastUpdated();

}
