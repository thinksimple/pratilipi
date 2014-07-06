package com.claymus.data.transfer;

import java.util.Date;

public interface User {

	String getId();

	void setId( String id );
	
	String getPassword();

	void setPassword( String password );

	String getName();

	void setName( String name );

	String getEmail();

	void setEmail( String email );

	String getPhone();

	void setPhone( String phone );

	Date getSignUpDate();
	
	void setSignUpDate( Date date );

}
