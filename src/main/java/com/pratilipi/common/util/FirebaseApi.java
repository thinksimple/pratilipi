package com.pratilipi.common.util;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
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
	private static final String DATABASE_URL = "https://prod-pratilipi.firebaseio.com/";

	
	private static void initialiseFirebase() {
		if( FirebaseApp.getApps().size() != 0 )
			return;
		String serviceAccountKey = DataAccessorFactory.getDataAccessor()
				.getAppProperty( AppProperty.SERVICE_ACCOUNT_FIREBASE )
				.getValue();
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setServiceAccount( IOUtils.toInputStream( serviceAccountKey, StandardCharsets.UTF_8 ) )
				.setDatabaseUrl( DATABASE_URL )
				.build();
		FirebaseApp.initializeApp( options );
	}
	
	private static String getFcmServerKey() {
		return DataAccessorFactory.getDataAccessor()
				.getAppProperty( AppProperty.FCM_SERVER_KEY )
				.getValue();
	}


	public static String sendCloudMessage( List<String> fcmTokenList, String message, String tag )
			throws UnexpectedServerException {

		Map<String, String> headersMap = new HashMap<>();
		headersMap.put( "Authorization", "key=" + getFcmServerKey() );

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
	
	public static String getCustomTokenForUser( Long userId ) {
		initialiseFirebase();
		return FirebaseAuth.getInstance().createCustomToken( userId.toString() );
	}

}
