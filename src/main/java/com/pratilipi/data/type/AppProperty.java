package com.pratilipi.data.type;

public interface AppProperty extends GenericOfyType {
	
	String FACEBOOK_CREDENTIALS = "Facebook.Credentials";
	
	
	String getId();
	
	<T> T getValue();

	<T> void setValue( T value );
	
}