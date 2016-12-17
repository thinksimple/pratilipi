package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.UserFollowState;

public interface UserAuthor extends GenericOfyType {

	String getId();
	
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );


	@Deprecated
	Boolean isFollowing();
	
	@Deprecated
	void setFollowing( Boolean isFollowing );
	
	@Deprecated
	Date getFollowingSince();
	
	@Deprecated
	void setFollowingSince( Date followingSince );

	
	UserFollowState getState();
	
	void setState( UserFollowState state );

	Date getFollowDate();
	
	void setFollowDate( Date date );
	
}
