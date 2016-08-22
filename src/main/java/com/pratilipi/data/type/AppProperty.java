package com.pratilipi.data.type;

public interface AppProperty extends GenericOfyType {
	
	String WORKER_ACCESS_TOKEN_ID = "Module.Worker.AccessToken";
	
	String FACEBOOK_CREDENTIALS = "Facebook.Credentials";
	
	String FCM_SERVER_KEY = "Firebase.CloudMessaging.ServerKey";
	
	
	
	String getId();
	
	<T> T getValue();

	<T> void setValue( T value );
	
}