package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.NotificationData;
import com.pratilipi.data.type.Notification;
import com.pratilipi.filter.AccessTokenFilter;

public class NotificationDataUtil {
	
	public static boolean hasAccessToListData( Long userId ) {
		
		return AccessTokenFilter.getAccessToken().getUserId().equals( userId );
		
	}
	
	
	public static NotificationData createNotificationData( Notification notification ) {
		
		return new NotificationData();
		
	}

	
	public static DataListCursorTuple<NotificationData> getNotificationList( Long userId, String cursor, Integer resultCount )
			throws InsufficientAccessException {
		
		if( ! hasAccessToListData( userId ) )
			throw new InsufficientAccessException();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DataListCursorTuple<Notification> notificationListCursorTuple = dataAccessor.getNotificationList( userId, cursor, resultCount );
		
		List<NotificationData> notificationDataList = new ArrayList<>( notificationListCursorTuple.getDataList().size() );
		for( Notification notification : notificationListCursorTuple.getDataList() )
			notificationDataList.add( createNotificationData( notification ) );
		
		return new DataListCursorTuple<>(
				notificationDataList,
				notificationListCursorTuple.getCursor() );
		
	}
	
}