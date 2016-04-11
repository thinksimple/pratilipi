package com.pratilipi.data.type;

import java.util.Date;

public interface BlogPost {

	Long getId();
	
	Long getBlogId();

	void setBlogId( Long blogId );

	
	String getTitle();
	
	void setTitle( String title );
	
	String getTitleEn();
	
	void setTitleEn( String titleEn );
	

	String getContent();

	void setContent( String content );

		
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
	Date getLastUpdated();

	void setLastUpdated( Date lastUpdated );
	
}
