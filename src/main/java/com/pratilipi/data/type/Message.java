package com.pratilipi.data.type;

public interface Message extends GenericOfyType {

	Long getId();
	
	String getName();
	
	void setName( String name );
	
	String getEmail();
	
	void setEmail( String email );
	
	String getSubject();
	
	void setSubject( String subject );
	
	String getBody();

	void setBody( String message );
	
}