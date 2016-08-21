package com.pratilipi.data.type;

public interface AppProperty extends GenericOfyType {
	
	String FACEBOOK_CREDENTIALS = "Facebook.Credentials";
	
	String WORKER_ACCESS_TOKEN_ID = "Pratilipi.Worker.AccessTokenId";
	
	
	String getId();
	
	<T> T getValue();

	<T> void setValue( T value );
	
}