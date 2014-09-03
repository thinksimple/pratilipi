package com.pratilipi.data.transfer;

import java.util.Date;

public interface Language {

	Long getId();
	
	String getName();
	
	void setName( String name );
	
	String getNameEn();
	
	void setNameEn( String nameEn );
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}
