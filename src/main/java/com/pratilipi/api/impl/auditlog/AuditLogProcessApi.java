package com.pratilipi.api.impl.auditlog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Notification;

@SuppressWarnings("serial")
@Bind( uri = "/auditlog/process" )
public class AuditLogProcessApi extends GenericApi {

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
		
		
		Gson gson = new Gson();
		for( AuditLog auditLog : auditLogDataListCursorTuple.getDataList() ) {
			
			JsonObject entityData = gson.fromJson( auditLog.getEventDataNew(), JsonElement.class ).getAsJsonObject();
			
			if( auditLog.getAccessType() == AccessType.PRATILIPI_UPDATE ) {
				
				PratilipiState pratilipiState = PratilipiState.valueOf( entityData.get( "STATE" ).getAsString() );
				if( pratilipiState != PratilipiState.PUBLISHED )
					continue;
				
				AccessToken accessToken = dataAccessor.getAccessToken( auditLog.getAccessId() );
				if( accessToken.getUserId().equals( SystemProperty.SYSTEM_USER_ID ) )
					continue;
				
				Long pratilipiId = entityData.get( "PRATILIPI_ID" ).getAsLong();
				Long authorId = entityData.get( "AUTHOR_ID" ).getAsLong();
				
				List<Long> followerUserIdList = dataAccessor.getUserAuthorFollowList( null, authorId, null, null, null ).getDataList();
				
				List<Notification> existingNotificationList = dataAccessor.getNotificationListIterator( null, NotificationType.PRATILIPI_ADD, pratilipiId, null, null ).list();
				for( Notification notification : existingNotificationList )
					followerUserIdList.remove( notification.getUserId() );

				List<Notification> notificationList = new ArrayList<>( followerUserIdList.size() );
				for( Long followerUserId : followerUserIdList ) {
					Notification notification = dataAccessor.newNotification(
							followerUserId,
							NotificationType.PRATILIPI_ADD,
							pratilipiId );
					notification.addAuditLogId( auditLog.getId() );
					notification.setFcmPending( true );
					notification.setLastUpdated( new Date() );
					notificationList.add( notification );
				}
				notificationList = dataAccessor.createOrUpdateNotificationList( notificationList );
				
			} else if( auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING ) {
				
				Long userId = entityData.get( "USER_ID" ).getAsLong(); // Follower
				Long authorId = entityData.get( "AUTHOR_ID" ).getAsLong(); // Followed
				boolean following = entityData.get( "FOLLOWING" ).getAsBoolean();
				Author author = dataAccessor.getAuthor( authorId );
				if( author.getUserId() == null ) // Followed
					continue;
				
				Notification notification = dataAccessor.getNotification( author.getUserId(), NotificationType.AUTHOR_FOLLOW, authorId );
				
				if( notification == null || ( following && ! _isToday( notification.getCreationDate() ) ) )
					notification = dataAccessor.newNotification(
							author.getUserId(),
							NotificationType.AUTHOR_FOLLOW,
							authorId );

				if( following ) {
					notification.addDataId( userId, auditLog.getId() );
					if( notification.getState() == NotificationState.READ )
						notification.setState( NotificationState.UNREAD );
				} else {
					notification.removeDataId( userId, auditLog.getId() );
					// Do NOT update lastUpdated date.
				}
				
				notification.setFcmPending( true );
				notification.setLastUpdated( new Date() );
				notification = dataAccessor.createOrUpdateNotification( notification );
				
			} // End of if( auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING )
			
		} // End of for
		
		
		// Updating AppProperty.
		if( auditLogDataListCursorTuple.getDataList().size() > 0 ) {
			appProperty.setValue( auditLogDataListCursorTuple.getCursor() );
			appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		}
		
		return new GenericResponse();
		
	}
	
	private boolean _isToday( Date date ) {
		Long time = new Date().getTime();
		time = time - time % TimeUnit.DAYS.toMillis( 1 ); // 00:00 AM GMT
		time = time - TimeUnit.MINUTES.toMillis( 330 ); // 00:00 AM IST
		return date.getTime() > time;
	}
	
}