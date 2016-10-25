package com.pratilipi.data.type;

public interface AppProperty extends GenericOfyType {
	
	String API_ACCESSTOKEN_CLEANUP = "Api.AccessTokenCleanup";
	String API_NOTIFICATION_PROCESS = "Api.NotificationProcess";
	
	String WORKER_ACCESS_TOKEN_ID = "Module.Worker.AccessToken";
	
	String SERVICE_ACCOUNT_FIREBASE = "ServiceAccount.Firebase.Key";
	
	String GOOGLE_WEB_CLIENT_ID = "Google.WebClient.Id";
	String GOOGLE_ANDROID_CLIENT_ID = "Google.AndroidClient.Id";
	
	String FACEBOOK_CREDENTIALS = "Facebook.Credentials";
	
	String FCM_SERVER_KEY = "Firebase.CloudMessaging.ServerKey";

	
	String getId();
	
	<T> T getValue();

	<T> void setValue( T value );
	
}