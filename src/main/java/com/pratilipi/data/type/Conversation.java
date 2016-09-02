package com.pratilipi.data.type;

import java.util.Date;

public interface Conversation extends GenericOfyType {

	String getId();
	
	String getTitle();
	
	void setTitle( String title );
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );

	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );

}