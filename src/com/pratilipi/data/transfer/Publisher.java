package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Publisher extends Serializable {

	Long getId();
	
	String getName();
	
	void setName( String name );
	
	String getEmail();
	
	void setEmail( String email );
	
	Date getRegistrationDate();
	
	void setRegistrationDate( Date registrationDate );
	
}
