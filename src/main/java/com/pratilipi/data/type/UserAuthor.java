package com.pratilipi.data.type;

import java.util.Date;

public interface UserAuthor {

	String getId();
	
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );


	Boolean isFollowing();
	
	void setFollowing( Boolean isFollowing );

	Date getFollowingSince();
	
	void setFollowingSince( Date followingSince );
	
}
