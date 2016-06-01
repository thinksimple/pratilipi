package com.pratilipi.data.type;

import java.util.Date;
import java.util.List;

public interface CommentDoc {
	
	Long getUserId();
	
	void setUserId( Long userId );

	
	String getContent();
	
	void setContent( String content );
	
	Date getDate();
	
	void setDate( Date date );

	
	List<Long> getLikedByUserIds();
	
	void setLikedByUserIds( List<Long> likedByUserIds );
	
}
