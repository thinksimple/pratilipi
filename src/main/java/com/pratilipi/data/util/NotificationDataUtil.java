package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.I18nGroup;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.common.type.RequestParameter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.NotificationData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.Notification;
import com.pratilipi.data.type.User;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.filter.UxModeFilter;

public class NotificationDataUtil {
	
	public static boolean hasAccessToListData( Long userId ) {
		
		return AccessTokenFilter.getAccessToken().getUserId().equals( userId );
		
	}
	
	
	public static int getNewNotificationCount( Long userId ) throws InsufficientAccessException {
		
		if( ! hasAccessToListData( userId ) )
			throw new InsufficientAccessException();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( userId );
		
		return dataAccessor.getNotificationCount( userId, user.getLastNotified() );
		
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
				if( userIdList.size() <= 3 )
					userIdList.addAll( notification.getDataIds() );
				else
					userIdList.addAll( notification.getDataIds().subList( notification.getDataIds().size() - 4, notification.getDataIds().size() ) );
			}
		}
		
		Map<Long, UserData> users = UserDataUtil.createUserDataList( userIdList, true );
		List<PratilipiData> pratilipiDataList = PratilipiDataUtil.createPratilipiDataList( pratilipiIdList, true );
		Map<Long, PratilipiData> pratilipis = new HashMap<>( pratilipiDataList.size() );
		for( PratilipiData pratilipiData : pratilipiDataList )
			pratilipis.put( pratilipiData.getId(), pratilipiData );
		
		Map<String, String> i18ns = dataAccessor.getI18nStrings( I18nGroup.NOTIFICATION, UxModeFilter.getDisplayLanguage() );
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
				notificationData.setMessage(
						"<b>" + authorName + "</b> "
						+ i18ns.get( "notification_has_published" )
						+ " <b>" + pratilipiTitle + "</b>" );
				notificationData.setSourceUrl( pratilipiData.getPageUrl() );
			
			} else 	if( notification.getType() == NotificationType.AUTHOR_FOLLOW ) {
				
				if( notification.getDataIds().size() == 0 ) {
					continue;
				} else if( notification.getDataIds().size() == 1 ) {
					notificationData.setMessage(
							"<b>" + users.get( notification.getDataIds().get( 0 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_has_followed" ) );
				} else if( notification.getDataIds().size() == 2 ) {
					notificationData.setMessage(
							"<b>" + users.get( notification.getDataIds().get( 1 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_and" )
							+ " <b>" + users.get( notification.getDataIds().get( 0 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_have_followed" ) );
				} else if( notification.getDataIds().size() == 3 ) {
					notificationData.setMessage(
							"<b>" + users.get( notification.getDataIds().get( 2 ) ).getDisplayName() + "</b>, "
							+ "<b>" + users.get( notification.getDataIds().get( 1 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_and" )
							+ " <b>" + users.get( notification.getDataIds().get( 0 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_have_followed" ) );
				} else {
					notificationData.setMessage(
							"<b>" + users.get( notification.getDataIds().get( notification.getDataIds().size() - 1 ) ).getDisplayName() + "</b>, "
							+ "<b>" + users.get( notification.getDataIds().get( notification.getDataIds().size() - 2 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_and" )
							+ " " + ( notification.getDataIds().size() - 2 ) + " "
							+ i18ns.get( "notification_others_have_followed" ) );
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