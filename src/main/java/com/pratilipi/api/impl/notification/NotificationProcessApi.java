package com.pratilipi.api.impl.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.NotificationState;
import com.pratilipi.common.util.FirebaseApi;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.NotificationData;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.Notification;
import com.pratilipi.data.util.NotificationDataUtil;


@SuppressWarnings("serial")
@Bind( uri = "/notification/process" )
public class NotificationProcessApi extends GenericApi {

	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Fetching AppProperty
		AppProperty appProperty = dataAccessor.getAppProperty( AppProperty.API_NOTIFICATION_PROCESS );
		if( appProperty == null )
			appProperty = dataAccessor.newAppProperty( AppProperty.API_NOTIFICATION_PROCESS );

		
		DataListCursorTuple<Notification> notificationListCursorTuple = dataAccessor.getNotificationListOrderByLastUpdated( (String) appProperty.getValue(), 1000 );
		List<Notification> notificationList = notificationListCursorTuple.getDataList();
		List<NotificationData> notificationDataList = NotificationDataUtil.createNotificationDataList( notificationList, null, true );

		
		// Writing to Firebase Database
		Map<Long, List<Long>> userIdNotifIdListMap = new HashMap<Long, List<Long>>();
		for( NotificationData notificationData : notificationDataList ) {

			if( notificationData.getMessage() == null )
				continue;
			if( notificationData.getState() != NotificationState.UNREAD )
				continue;

			List<Long> notificationIdList = userIdNotifIdListMap.get( notificationData.getUserId() );
			if( notificationIdList == null )
				notificationIdList = new LinkedList<>();

			notificationIdList.add( notificationData.getId() );
			userIdNotifIdListMap.put( notificationData.getUserId(), notificationIdList );

		}
		FirebaseApi.updateUserNotificationData( userIdNotifIdListMap );


		// FCM Cloud Messaging
		List<Notification> notificationListToPersist = new ArrayList<>( notificationList.size() );
		for( int i = 0; i < notificationList.size(); i++ ) {
			
			Notification notification = notificationList.get( i );
			NotificationData notificationData = notificationDataList.get( i );
			
			if( notificationData.getMessage() == null )
				continue;
			if( notification.getState() != NotificationState.UNREAD )
				continue;


			List<String> fcmTokenList = dataAccessor.getFcmTokenList( notification.getUserId() );
			if( fcmTokenList.size() == 0 )
				continue;
			
			String fcmResponse = FirebaseApi.sendCloudMessage(
					fcmTokenList,
					notificationData.getMessage(),
					notification.getId().toString(),
					notification.getType().getAndroidHandler(),
					notification.getSourceId().toString() );
			
			if( notification.getFcmResponse() == null )
				notification.setFcmResponse( fcmResponse );
			else
				notification.setFcmResponse( notification.getFcmResponse() + "\n" + fcmResponse );
			notificationListToPersist.add( notification );
			
		}
		dataAccessor.createOrUpdateNotificationList( notificationListToPersist );

		
		// Updating AppProperty
		if( notificationList.size() > 0 ) {
			appProperty.setValue( notificationListCursorTuple.getCursor() );
			dataAccessor.createOrUpdateAppProperty( appProperty );
		}
		
		
		return new GenericResponse();
		
	}
	
}