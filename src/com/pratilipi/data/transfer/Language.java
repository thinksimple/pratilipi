package com.pratilipi.data.transfer;

import java.util.Date;

public interface Language {

	Long getId();
	
	String getName();
	
	void setName( String Name );
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}
