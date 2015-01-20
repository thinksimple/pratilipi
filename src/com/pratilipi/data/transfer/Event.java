package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Event extends Serializable {

	Long getId();
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getLanguageId();
	
	void setLanguageId( Long languageId );
	
	String getName();
	
	void setName( String name );
	
	String getNameEn();
	
	void setNameEn( String nameEn );
	
	String getEmail();
	
	void setEmail( String email );
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
	Date getStartDate();
	
	void setStartDate( Date startDate );
	
	Date getEndDate();
	
	void setEndDate( Date endDate );
	
}
