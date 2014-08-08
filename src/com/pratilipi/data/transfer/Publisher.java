package com.pratilipi.data.transfer;

import java.util.Date;

public interface Publisher {

	Long getId();
	
	String getName();
	
	void setName( String name );
	
	String getEmail();
	
	void setEmail( String email );
	
	Date getRegistrationDate();
	
	void setRegistrationDate( Date registrationDate );
	
}
