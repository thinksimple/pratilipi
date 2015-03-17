package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Author extends Serializable {
	
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
	
	Boolean hasCustomCover();

	void setCustomCover( Boolean customCover );

	String getSummary();
	
	void setSummary( String summary );
	
	String getEmail();
	
	void setEmail( String email );
	
	Date getRegistrationDate();
	
	void setRegistrationDate( Date registrationDate );

	Long getContentPublished();
	
	void setContentPublished( Long contentPublished );
	
	Long getTotalReadCount();
	
	void setTotalReadCount( Long totalReadCount );
	
	Date getLastProcessDate();

	void setLastProcessDate( Date lastProcessDate );

	Date getNextProcessDate();

	void setNextProcessDate( Date nextProcessDate );

}
