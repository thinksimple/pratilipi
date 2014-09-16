package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Language extends Serializable {

	Long getId();
	
	String getName();
	
	void setName( String name );
	
	String getNameEn();
	
	void setNameEn( String nameEn );
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}
