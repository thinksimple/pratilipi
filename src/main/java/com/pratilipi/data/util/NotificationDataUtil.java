package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.common.type.RequestParameter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.NotificationData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.Notification;
import com.pratilipi.filter.AccessTokenFilter;

public class NotificationDataUtil {
	
	public static boolean hasAccessToListData( Long userId ) {
		
		return AccessTokenFilter.getAccessToken().getUserId().equals( userId );
		
	}
	
	
	public static DataListCursorTuple<NotificationData> getNotificationList( Long userId, String cursor, Integer resultCount )
			throws InsufficientAccessException, UnexpectedServerException {
		
		if( ! hasAccessToListData( userId ) )
			throw new InsufficientAccessException();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DataListCursorTuple<Notification> notificationListCursorTuple =
				dataAccessor.getNotificationList( userId, null, (String) null, cursor, resultCount );
		
		List<Long> userIdList = new LinkedList<>();
		List<Long> pratilipiIdList = new LinkedList<>();
		for( Notification notification : notificationListCursorTuple.getDataList() ) {
			if( notification.getType() == NotificationType.PRATILIPI_ADD ) {
				pratilipiIdList.add( notification.getSourceIdLong() );
			} else if( notification.getType() == NotificationType.AUTHOR_FOLLOW ) {
				userIdList.addAll( notification.getDataIds() );
			}
		}
		
		Map<Long, UserData> users = UserDataUtil.createUserDataList( userIdList, true );
		List<PratilipiData> pratilipiDataList = PratilipiDataUtil.createPratilipiDataList( pratilipiIdList, true );
		Map<Long, PratilipiData> pratilipis = new HashMap<>( pratilipiDataList.size() );
		for( PratilipiData pratilipiData : pratilipiDataList )
			pratilipis.put( pratilipiData.getId(), pratilipiData );
		
		List<NotificationData> notificationDataList = new ArrayList<>( notificationListCursorTuple.getDataList().size() );
		for( Notification notification : notificationListCursorTuple.getDataList() ) {
			
			NotificationData notificationData = new NotificationData( notification.getId() );
			
			if( notification.getType() == NotificationType.PRATILIPI_ADD ) {
				
				PratilipiData pratilipiData = pratilipis.get( notification.getSourceIdLong() );
				String pratilipiTitle = pratilipiData.getTitle() == null
						? pratilipiData.getTitleEn()
						: pratilipiData.getTitle();
				String authorName = pratilipiData.getAuthor().getName() == null
						? pratilipiData.getAuthor().getNameEn()
						: pratilipiData.getAuthor().getName();
				notificationData.setMessage( "<b>" + pratilipiTitle + "</b> published by <b>" + authorName + "</b>." );
				notificationData.setSourceUrl( pratilipiData.getPageUrl() );
			
			} else 	if( notification.getType() == NotificationType.AUTHOR_FOLLOW ) {
				
				if( notification.getDataIds().size() > 0 ) {
					String notificationMsg = "";
					for( Long followerUserId : notification.getDataIds() )
						notificationMsg += "<b>" + users.get( followerUserId ).getDisplayName() + "</b>, ";
					notificationMsg = notificationMsg.substring( 0, notificationMsg.length() - 2 );
					notificationMsg += " followed you.";
					notificationData.setMessage( notificationMsg );
				}
				
				notificationData.setSourceUrl( "/followers?" + RequestParameter.NOTIFICATION_ID.getName() + "=" + notification.getId() );
			
			}
			
			notificationData.setState( notification.getState() );
			
			notificationDataList.add( notificationData );
			
		}
		
		return new DataListCursorTuple<>(
				notificationDataList,
				notificationListCursorTuple.getCursor() );
		
	}
	
}