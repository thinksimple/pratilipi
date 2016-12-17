package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.UserFollowState;

public interface UserAuthor extends GenericOfyType {

	String getId();
	
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );


	Boolean isFollowing();
	
	void setFollowing( Boolean isFollowing );
	
	UserFollowState getState();
	
	void setState( UserFollowState state );


	Date getFollowingSince();
	
	void setFollowingSince( Date followingSince );
	
}
