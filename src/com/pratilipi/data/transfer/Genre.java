package com.pratilipi.data.transfer;

import java.util.Date;

public interface Genre {

	Long getId();
	
	String getName();
	
	void setName( String name );
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}
