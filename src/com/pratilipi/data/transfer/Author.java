package com.pratilipi.data.transfer;

import java.util.Date;

public interface Author {
	
	Long getId();

	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getLanguageId();
	
	void setLanguageId( Long languageId );
	
	String getFirstName();
	
	void setFirstName( String firstName );
	
	String getLastName();
	
	void setLastName( String lastName );
	
	String getPenName();
	
	void setPenName( String penName );
	
	String getFirstNameEn();
	
	void setFirstNameEn( String firstNameEn );
	
	String getLastNameEn();
	
	void setLastNameEn( String lastNameEn );
	
	String getPenNameEn();
	
	void setPenNameEn( String penNameEn );
	
	String getSummary();
	
	void setSummary( String summary );
	
	String getEmail();
	
	void setEmail( String email );
	
	Date getRegistrationDate();
	
	void setRegistrationDate( Date registrationDate );

}
