package com.pratilipi.common.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
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
	
	public static void updateUserNotificationData(
			Long userId,
			final List<Long> notifIdListToAdd,
			final List<Long> notifIdListToRemove,
			final Async async ) {

		
		initialiseFirebase();


		DatabaseReference databaseReference = FirebaseDatabase.getInstance()
				.getReference()
				.child( DATABASE_NOTIFICATION_TABLE )
				.child( userId.toString() );


		databaseReference.runTransaction( new Transaction.Handler() {

			@Override
			public Transaction.Result doTransaction( MutableData mutableData ) {

				// Current list of notificationIds with Firebase
				List<Long> notifIdList = new LinkedList<>();
				if( mutableData.getValue() != null ) {
					JsonArray jArray = mutableData
							.getValue( JsonElement.class ).getAsJsonObject()
							.get( "notificationIdList" ).getAsJsonArray();
					for( int i = 0; i < jArray.size(); i++ )
						notifIdList.add( jArray.get(i).getAsLong() );
				}

				// Add/Remove notificationIds
				notifIdList.removeAll( notifIdListToAdd ); // Remove ids first to avoid duplicates
				notifIdList.removeAll( notifIdListToRemove );
				notifIdList.addAll( notifIdListToAdd );
				
				// Updating Firebase
				Map<String, Object> notifJson = new HashMap<String, Object>();
				notifJson.put( "notificationIdList", notifIdList );
				notifJson.put( "newNotificationCount", notifIdList.size() );
				mutableData.setValue( notifJson );
				return Transaction.success( mutableData );

			}

			@Override
			public void onComplete( DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot ) {
				
				if( committed ) { // Transaction successful
					
					async.exec();
				
//				} else if( databaseError == null ) { // Transaction aborted
				
				} else { // Transaction failed
					
					logger.log( Level.SEVERE, "Transaction failed with error code : " + databaseError.getCode() );
					
				}
				
			}

		} );
		
	}

}
