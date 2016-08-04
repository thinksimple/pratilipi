package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.Notification;
import com.pratilipi.data.type.User;
import com.pratilipi.filter.AccessTokenFilter;

public class NotificationDataUtil {
	
	public static boolean hasAccessToListData( Long userId ) {
		
		return AccessTokenFilter.getAccessToken().getUserId().equals( userId );
		
	}
	
	
	public static DataListCursorTuple<String> getNotificationList( Long userId, String cursor, Integer resultCount )
			throws InsufficientAccessException {
		
		if( ! hasAccessToListData( userId ) )
			throw new InsufficientAccessException();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DataListCursorTuple<Notification> notificationListCursorTuple = dataAccessor.getNotificationList( userId, cursor, resultCount );
		
		List<Long> userIdList = new LinkedList<>(); // TODO: change it to list set
		for( Notification notification : notificationListCursorTuple.getDataList() ) {
			if( notification.getType() == NotificationType.AUTHOR_FOLLOW ) {
				userIdList.add( notification.getUserId() );
				if( notification.getData() != null )
					userIdList.addAll( (List<Long>) notification.getData() );
			}
		}
		
		Map<Long, User> users = dataAccessor.getUsers( userIdList );
		
		List<String> notifications = new ArrayList<>( notificationListCursorTuple.getDataList().size() );
		for( Notification notification : notificationListCursorTuple.getDataList() ) {
			if( notification.getData() == null )
				continue;
			String notificationStr = "";
			for( Long uId : (List<Long>) notification.getData() ) {
				UserData user = UserDataUtil.createUserData( users.get( uId ) );
				notificationStr += "<a href=" + user.getProfilePageUrl() + ">" + user.getDisplayName() + "</a>, ";
			}
			notificationStr = notificationStr.substring( 0, notificationStr.length() - 2 )
					+ "followed you.";
			notifications.add( notificationStr );
		}
		
		return new DataListCursorTuple<>(
				notifications,
				notificationListCursorTuple.getCursor() );
		
	}
	
}