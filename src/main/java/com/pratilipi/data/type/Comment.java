package com.pratilipi.data.type;

import java.util.Date;

public interface Comment extends GenericOfyType {

	Long getId();
	
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	String getContentId();
	
	void setContentId( String contentId );
	
	
	String getCommentText();
	
	void setCommentText( String commentText );
	

	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );

}