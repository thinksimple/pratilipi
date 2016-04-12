package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.Language;

public interface Blog {

	Long getId();

	
	String getTitle();
	
	void setTitle( String title );
	
	String getTitleEn();
	
	void setTitleEn( String titleEn );
	
	Language getLanguage();
	
	void setLanguage( Language language );
	
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
	Date getLastUpdated();

	void setLastUpdated( Date lastUpdated );

}
