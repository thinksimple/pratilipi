package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.Gender;
import com.pratilipi.common.type.Language;

public interface Author extends Serializable {
	
	Long getId();

	Long getUserId();

	void setUserId( Long userId );

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

	
	Gender getGender();
	
	void setGender( Gender gender );
	
	String getDateOfBirth();
	
	void setDateOfBirth( String dateOfBirth );
	
	
	@Deprecated
	String getEmail();
	
	@Deprecated
	void setEmail( String email );
	
	Language getLanguage();
	
	void setLanguage( Language language );
	
	String getSummary();
	
	void setSummary( String summary );
	

	AuthorState getState();
	
	void setState( AuthorState state );
	
	Boolean hasCustomImage();

	void setCustomImage( Boolean customImage );

	Date getRegistrationDate();
	
	void setRegistrationDate( Date registrationDate );

	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );

	
	Long getFollowCount();
	
	void setFollowCount( Long followCount );
		
	Integer getContentPublished();
	
	void setContentPublished( Integer contentPublished );
	
	Long getTotalReadCount();
	
	void setTotalReadCount( Long totalReadCount );
	
	Long getTotalFbLikeShareCount();
	
	void setTotalFbLikeShareCount( Long totalFbLikeShareCount );

	
}
