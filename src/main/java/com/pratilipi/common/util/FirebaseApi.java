package com.pratilipi.common.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;

public class FirebaseApi {

	private static Boolean hasBeenInitialized = false;

	private static final Logger logger =
			Logger.getLogger( FirebaseApi.class.getName() );

	
	private static final String CLOUD_MESSAGING_API_URL = "https://fcm.googleapis.com/fcm/send";
	private static final String DATABASE_URL = "https://prod-pratilipi.firebaseio.com/";
	private static final String DATABASE_NOTIFICATION_TABLE = "NOTIFICATION";

	
	private synchronized static void initialiseFirebase() {

		if( hasBeenInitialized )
			return;

		logger.log( Level.INFO, "Firebase Apps: " + FirebaseApp.getApps() );
		logger.log( Level.INFO, "Initialising Firebase..." );

		String serviceAccountKey = DataAccessorFactory.getDataAccessor()
				.getAppProperty( AppProperty.SERVICE_ACCOUNT_FIREBASE )
				.getValue();

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setServiceAccount( IOUtils.toInputStream( serviceAccountKey, StandardCharsets.UTF_8 ) )
				.setDatabaseUrl( DATABASE_URL )
				.build();

		FirebaseApp.initializeApp( options );
		hasBeenInitialized = true;

		logger.log( Level.INFO, "Firebase has been initialised successfully!" );
	}
	
	private static String getFcmServerKey() {
		return DataAccessorFactory.getDataAccessor()
				.getAppProperty( AppProperty.FCM_SERVER_KEY )
				.getValue();
	}


	public static String sendCloudMessage( List<String> fcmTokenList, String message, String tag, String androidHandler, String sourceId )
			throws UnexpectedServerException {

		Map<String, String> headersMap = new HashMap<>();
		headersMap.put( "Authorization", "key=" + getFcmServerKey() );

		JsonObject notificationJson = new JsonObject();
		notificationJson.addProperty( "body", message );
		notificationJson.addProperty( "tag", tag );
		notificationJson.addProperty( "sound", "default" );
		notificationJson.addProperty( "splash", "pratilipi_icon" );
		notificationJson.addProperty( "click_action", androidHandler );
		
		JsonObject dataJson = new JsonObject();
		dataJson.addProperty( "sourceId", sourceId );
		
		JsonObject bodyJson = new JsonObject();
		bodyJson.add( "registration_ids", new Gson().toJsonTree( fcmTokenList ) );
		bodyJson.add( "notification", notificationJson );
		bodyJson.add( "data", dataJson );

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

	public static void resetUserNotificationData( Long userId ) {

		initialiseFirebase();

		DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().
											child( DATABASE_NOTIFICATION_TABLE ).
											child( userId.toString() );

		Map<String, Object> notificationJson = new HashMap<String, Object>();
		notificationJson.put( "newNotificationCount", 0 );
		notificationJson.put( "notificationIdList", new ArrayList<Long>() );
		databaseReference.setValue( notificationJson );
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
	
	public static void updateUserNotificationData() {

		initialiseFirebase();

		Random ra = new Random();
		String node = ra.nextDouble() < 0.5 ? "555" : "565";
		DatabaseReference upvotesRef = FirebaseDatabase.getInstance().getReference().child( "TEST" ).child( node );
		upvotesRef.runTransaction( new Transaction.Handler() {
			@Override
			public Transaction.Result doTransaction( MutableData mutableData ) {
				Random r = new Random();
				if( mutableData.getValue() != null ) {
					JsonObject jsn = new Gson().fromJson( mutableData.getValue().toString(), JsonElement.class ).getAsJsonObject();
					Integer newNotifCount = jsn.get( "newNotificationCount" ).getAsInt();
					List<Long> notificationIdList = new ArrayList<Long>();
					JsonArray jArray = jsn.get( "notificationIdList" ).getAsJsonArray();
					for( int i=0; i < jArray.size(); i++ )
						notificationIdList.add( Long.parseLong( jArray.get(i).getAsString() ) );
					notificationIdList.add( r.nextLong() );
					Map<String, Object> notificationJson = new HashMap<String, Object>();
					notificationJson.put( "newNotificationCount", newNotifCount + 1 );
					notificationJson.put( "notificationIdList", notificationIdList );
					mutableData.setValue( notificationJson );
				} else {
					Map<String, Object> notificationJson = new HashMap<String, Object>();
					notificationJson.put( "newNotificationCount", 1 );
					List<Long> notificationIdList = new ArrayList<Long>();
					notificationIdList.add( r.nextLong() );
					notificationJson.put( "notificationIdList", notificationIdList );
					mutableData.setValue( notificationJson );
				}

				return Transaction.success( mutableData );
			}

			@Override
			public void onComplete( DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot ) {
				if( committed ) {
					logger.log( Level.INFO, "Transaction completed successfully on : " + dataSnapshot.toString() );
				} else {
					logger.log( Level.SEVERE, "Transaction failed. Error code : " + databaseError.getCode() );
				}
					
			}
		} );
	}

}
