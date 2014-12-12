package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Publisher extends Serializable {

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
	
	String getSecret();
	
	void setSecret( String publisherSecret );
	
	Date getRegistrationDate();
	
	void setRegistrationDate( Date registrationDate );
	
}
