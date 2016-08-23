package com.pratilipi.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;

public class FirebaseApi {

	private static final Logger logger =
			Logger.getLogger( FirebaseApi.class.getName() );

	private static final String CLOUD_MESSAGING_API_URL = "https://fcm.googleapis.com/fcm/send";

	private static void initialiseFirebase() {

		FirebaseOptions options = null;
		String text = DataAccessorFactory.getDataAccessor().getAppProperty( AppProperty.SERVICE_ACCOUNT_FIREBASE ).getValue();
		text.replaceAll( "\n", "\\n" );
		logger.log( Level.INFO, "service account firebase json = " + text );
		InputStream stream = new ByteArrayInputStream( text.getBytes( StandardCharsets.UTF_8 ) );

		options = new FirebaseOptions.Builder()
				.setServiceAccount( stream )
				.setDatabaseUrl( "https://prod-pratilipi.firebaseio.com/" )
				.build();

		FirebaseApp.initializeApp( options );

	}

	public static void test() {
		if( FirebaseApp.getApps().size() == 0 ) {
			initialiseFirebase();
		}
	}

	private static String getServerKey() {
		return DataAccessorFactory.getDataAccessor()
				.getAppProperty( AppProperty.FCM_SERVER_KEY )
				.getValue();
	}

	public static String sendCloudMessage( List<String> fcmTokenList, String message, String tag )
			throws UnexpectedServerException {

		Map<String, String> headersMap = new HashMap<>();
		headersMap.put( "Authorization", "key=" + getServerKey() );

		JsonObject notificationJson = new JsonObject();
		notificationJson.addProperty( "body", message );
		notificationJson.addProperty( "tag", tag );
		notificationJson.addProperty( "sound", "default" );
		notificationJson.addProperty( "icon", "ic_notification" );

		JsonObject bodyJson = new JsonObject();
		bodyJson.add( "registration_ids", new Gson().toJsonTree( fcmTokenList ) );
		bodyJson.add( "notification", notificationJson );

		String responsePayload = HttpUtil.doPost( CLOUD_MESSAGING_API_URL, headersMap, bodyJson );

		// Firebase might return with one or more error responses.
		for( JsonElement resultJson : new Gson().fromJson( responsePayload, JsonElement.class ).getAsJsonObject().get( "results" ).getAsJsonArray() )
			if( resultJson.getAsJsonObject().get( "error" ) != null )
				logger.log( Level.SEVERE, "Firebase responded with error: " + resultJson.getAsJsonObject().get( "error" ).getAsString() );

		
		return responsePayload;

	}

	public static void setDatabaseValue( Long userId, Integer newNotificationCount ) {

		if( FirebaseApp.getApps().size() == 0 ) {
			initialiseFirebase();
		}

		DatabaseReference databaseReference = FirebaseDatabase
			    .getInstance()
			    .getReference( "NOTIFICATION" );

		Map<String, Object> children = new HashMap<String, Object>();
		children.put( userId + "", newNotificationCount );
		databaseReference.updateChildren( children );

	}

}
