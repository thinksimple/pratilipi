package com.pratilipi.data.type;

import java.io.Serializable;

public interface AppProperty extends Serializable {
	
	String FACEBOOK_CREDENTIALS = "Facebook.Credentials";
	
	
	String getId();
	
	void setId(String string);

	<T> T getValue();

	<T> void setValue( T value );
	
}