package com.pratilipi.api.impl.notification;

import java.util.LinkedList;
import java.util.List;

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
import com.pratilipi.data.client.NotificationData;
import com.pratilipi.data.util.NotificationDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/notification/process" )
public class NotificationProcessApi extends GenericApi {

	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<Long> userIdList = new LinkedList<>();
		userIdList.add( dataAccessor.getUserByEmail( "prashant@pratilipi.com" ).getId() );
		userIdList.add( dataAccessor.getUserByEmail( "anuja@pratilipi.com" ).getId() );
		userIdList.add( dataAccessor.getUserByEmail( "raghu@pratilipi.com" ).getId() );
		
		
		List<NotificationData> notificationList = NotificationDataUtil.createNotificationDataList( dataAccessor.getNotificationListWithPendingFcm( 1000 ) );
		for( NotificationData notification : notificationList ) {
			if( ! userIdList.contains( notification.getUserId() ) )
				continue;
			if( notification.getState() != NotificationState.UNREAD )
				continue;
			List<String> fcmTokenList = dataAccessor.getFcmTokenList( notification.getUserId() );
			if( fcmTokenList.size() == 0 )
				continue;
			FirebaseApi.sendCloudMessage( fcmTokenList, notification.getMessage(), notification.getId().toString() );
		}

		return new GenericResponse();
		
	}
	
	
}