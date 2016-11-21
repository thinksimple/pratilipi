package com.pratilipi.api.impl.notification;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Notification;
import com.pratilipi.data.util.NotificationDataUtil;


@SuppressWarnings("serial")
@Bind( uri = "/notification/process" )
public class NotificationProcessApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( NotificationProcessApi.class.getName() );
	
	
	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<Notification> notifList = dataAccessor.getNotificationListWithFcmPending( 1000 );

		logger.log( Level.INFO, "Total pending notifs = " + notifList.size() ); // TODO: remove

		Map<Long, List<Notification>> userIdNotifListMap = new HashMap<>();
		for( Notification notif : notifList ) {
			List<Notification> userNotifList = userIdNotifListMap.get( notif.getUserId() );
			if( userNotifList == null ) {
				userNotifList = new LinkedList<>();
				userIdNotifListMap.put( notif.getUserId(), userNotifList );
			}
			userNotifList.add( notif );
		}

		for( Entry<Long, List<Notification>> entry : userIdNotifListMap.entrySet() ) {
			if( entry.getKey().equals( 5629499534213120L ) ) { // TODO: remove
				for( Notification n : entry.getValue() ) // TODO: remove
					logger.log( Level.INFO, ">> " + n.getKey() ); // TODO: remove
				NotificationDataUtil.dispatchNotification( entry.getKey(), entry.getValue() );
			}
		}
		
		return new GenericResponse();
		
	}
	
}