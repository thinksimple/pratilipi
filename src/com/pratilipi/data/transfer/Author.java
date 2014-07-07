package com.pratilipi.data.transfer;

import java.util.Date;

public interface Author {
	
	Long getId();
	
	String getFirstName();
	
	void setFirstName( String firstName );
	
	String getLastName();
	
	void setLastName( String lastName );
	
	String getEmail();
	
	void setEmail( String email );
	
	Date getRegistrationDate();
	
	void setRegistrationDate( Date registrationDate );

}
