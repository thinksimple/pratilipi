package com.pratilipi.common.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
	private static final String DATABASE_NOTIFICATION_TABLE = "NOTIFICATION";

	
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


	public static String sendCloudMessage( List<String> fcmTokenList, String message, String tag, String androidHandler )
			throws UnexpectedServerException {

		Map<String, String> headersMap = new HashMap<>();
		headersMap.put( "Authorization", "key=" + getFcmServerKey() );

		JsonObject notificationJson = new JsonObject();
		notificationJson.addProperty( "body", message );
		notificationJson.addProperty( "tag", tag );
		notificationJson.addProperty( "sound", "default" );
		notificationJson.addProperty( "icon", "pratilipi_icon" );
		notificationJson.addProperty( "click_action", androidHandler );
		
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

	public static void updateUserNotificationData( final Long notificationId, Long userId ) {

		initialiseFirebase();

		final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().
											child( DATABASE_NOTIFICATION_TABLE ).
											child( userId.toString() );

		databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {

			private void updateNode( Long newNotificationCount, List<Long> notificationIdList ) {
				Map<String, Object> notificationJson = new HashMap<String, Object>();
				notificationJson.put( "newNotificationCount", newNotificationCount );
				notificationJson.put( "notificationIdList", notificationIdList );
				databaseReference.setValue( notificationJson );
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onDataChange( DataSnapshot dataSnapshot ) {

				List<Long> notificationIdList = new ArrayList<Long>();
				Long newNotificationCount = 0L;

				if( dataSnapshot.getValue() != null ) {
					Map<String, Object> notificationJson = (Map<String, Object>) dataSnapshot.getValue();
					newNotificationCount = (Long) notificationJson.get( "newNotificationCount" );
					// User has been notified
					if( newNotificationCount > 0 )
						notificationIdList = (List<Long>) notificationJson.get( "notificationIdList" );
				}

				if( ! notificationIdList.contains( notificationId ) ) {
					newNotificationCount = newNotificationCount + 1;
					notificationIdList.add( notificationId );
					updateNode( newNotificationCount, notificationIdList );
				}

			}

			@Override
			public void onCancelled( DatabaseError databaseError ) {
				System.out.println( "The read failed: " + databaseError.getCode() );
			}

		});
	}

}
