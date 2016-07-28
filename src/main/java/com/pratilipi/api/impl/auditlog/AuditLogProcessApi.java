package com.pratilipi.api.impl.auditlog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.pratilipi.data.DataAccessorGaeImpl;
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
			appProperty = dataAccessor.newAppProperty( "Api.AuditLogProcess" );

		// Fetching list of audit logs
		DataListCursorTuple<AuditLog> auditLogDataListCursorTuple = dataAccessor.getAuditLogList( (String) appProperty.getValue(), 10000 );
		
		int count = 0;
		
		Gson gson = new Gson();
		for( AuditLog auditLog : auditLogDataListCursorTuple.getDataList() ) {
			
			if( auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING ) {
				
				JsonObject jsonObject = gson.fromJson( auditLog.getEventDataNew(), JsonElement.class ).getAsJsonObject();
				Long userId = jsonObject.get( "USER_ID" ).getAsLong();
				Long authorId = jsonObject.get( "AUTHOR_ID" ).getAsLong();
				boolean following = jsonObject.get( "FOLLOWING" ).getAsBoolean();
				
				Author author = dataAccessor.getAuthor( authorId );
				if( author.getUserId() == null )
					continue;
				Notification notification = dataAccessor.getNotification( author.getUserId(), NotificationType.AUTHOR_FOLLOW );
				
				if( following ) {
					if( notification == null || ( (List<Long>) notification.getData() ).size() >= 10 ) {
						notification = dataAccessor.newNotification();
						notification.setUserId( author.getUserId() );
						notification.setType( NotificationType.AUTHOR_FOLLOW );
						notification.setState( NotificationState.UNREAD );
						notification.setCreationDate( new Date() );
						notification.setLastUpdated( new Date() );
					} else {
						if( notification.getState() == NotificationState.READ )
							notification.setState( NotificationState.UNREAD );
						notification.setLastUpdated( new Date() );
					}
					List<Long> followingUserIdList = notification.getData();
					if( followingUserIdList == null ) {
						followingUserIdList = new ArrayList<>( 1 );
						followingUserIdList.add( userId );
						notification.setData( followingUserIdList );
						notification.addAuditLogId( auditLog.getId() );
					} else if( followingUserIdList.contains( userId ) ) {
						continue;
					} else {
						followingUserIdList.add( userId );
						notification.setData( followingUserIdList );
						notification.addAuditLogId( auditLog.getId() );
					}
				} else {
					if( notification == null ) {
						continue;
					} else {
						List<Long> followingUserIdList = notification.getData();
						if( followingUserIdList.contains( userId ) ) {
							followingUserIdList.remove( userId );
							notification.setData( followingUserIdList );
							notification.addAuditLogId( auditLog.getId() );
							notification.setLastUpdated( new Date() );
						} else {
							continue;
						}
					}
				}
				
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