package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.Language;

public interface Event extends Serializable {

	Long getId();
	
	String getName();
	
	void setName( String name );
	
	String getNameEn();
	
	void setNameEn( String nameEn );
	

	Language getLanguage();
	
	void setLanguage( Language language );
	
	String getSummary();

	void setSummary( String summary );


	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}
