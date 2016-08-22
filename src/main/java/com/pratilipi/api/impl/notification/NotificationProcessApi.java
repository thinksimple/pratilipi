package com.pratilipi.api.impl.notification;

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
import com.pratilipi.data.type.Notification;
import com.pratilipi.data.type.User;

@SuppressWarnings("serial")
@Bind( uri = "/notification/process" )
public class NotificationProcessApi extends GenericApi {

	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<Notification> notificationList = dataAccessor.getNotificationListWithPendingFcm( 1000 );
		User user = dataAccessor.getUserByEmail( "prashant@pratilipi.com" );
		
		for( Notification notification : notificationList ) {
			if( ! notification.getUserId().equals( user.getId() ) )
				continue;
			if( notification.getState() != NotificationState.READ )
				continue;
			if( notification.getFcmMsgId() != null )
				continue;
			// TODO: send FCM message
			List<String> fcmTokenList = dataAccessor.getFcmTokenList( notification.getUserId() );
			if( fcmTokenList.size() == 0 )
				continue;
			FirebaseApi.sendCloudMessage( fcmTokenList, "Test Message", notification.getId().toString() );
		}

		return new GenericResponse();
		
	}
	
	
}