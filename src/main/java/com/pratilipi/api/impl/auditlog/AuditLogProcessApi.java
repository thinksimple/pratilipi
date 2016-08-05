package com.pratilipi.api.impl.auditlog;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.NotificationState;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Notification;

@SuppressWarnings("serial")
@Bind( uri = "/auditlog/process" )
public class AuditLogProcessApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( AuditLogProcessApi.class.getName() );

	@Get
	public GenericResponse get( GenericRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Fetching AppProperty
		String appPropertyId = "Api.AuditLogProcess";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null )
			appProperty = dataAccessor.newAppProperty( appPropertyId );

		// Fetching list of audit logs
		DataListCursorTuple<AuditLog> auditLogDataListCursorTuple = dataAccessor.getAuditLogList(
				new Date( 1469989800000L ), // Mon Aug 01 00:00:00 IST 2016
				(String) appProperty.getValue(),
				10000 );
		
		int count = 0;
		
		Gson gson = new Gson();
		for( AuditLog auditLog : auditLogDataListCursorTuple.getDataList() ) {
			
			if( auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING ) {
				
				JsonObject jsonObject = gson.fromJson( auditLog.getEventDataNew(), JsonElement.class ).getAsJsonObject();
				Long userId = jsonObject.get( "USER_ID" ).getAsLong(); // Follower
				Long authorId = jsonObject.get( "AUTHOR_ID" ).getAsLong(); // Followed
				boolean following = jsonObject.get( "FOLLOWING" ).getAsBoolean();
				Author author = dataAccessor.getAuthor( authorId );
				if( author.getUserId() == null ) // Followed
					continue;
				Notification notification = dataAccessor.getNotification( author.getUserId(), NotificationType.AUTHOR_FOLLOW );
				
				if( notification == null || ( following && notification.getDataIds().size() >= 10 ) ) {
					notification = dataAccessor.newNotification();
					notification.setUserId( author.getUserId() ); // Followed
					notification.setType( NotificationType.AUTHOR_FOLLOW );
					notification.setSourceId( authorId ); // Followed
					notification.setState( NotificationState.UNREAD );
					notification.setCreationDate( new Date() );
				}

				if( following ) {
					notification.addDataId( userId, auditLog.getId() );
					if( notification.getState() == NotificationState.READ )
						notification.setState( NotificationState.UNREAD );
				} else {
					notification.removeDataId( userId, auditLog.getId() );
				}
				
				notification.setLastUpdated( new Date() );
				
				notification = dataAccessor.createOrUpdateNotification( notification );
				count++;
				
			} // End of if( auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING )
			
		} // End of for
		
		// Updating AppProperty.
		if( auditLogDataListCursorTuple.getDataList().size() > 0 ) {
			appProperty.setValue( auditLogDataListCursorTuple.getCursor() );
			appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		}
		
		logger.log( Level.INFO, "Created/updated " + count + " notifications." );
		
		return new GenericResponse();
		
	}
	
}