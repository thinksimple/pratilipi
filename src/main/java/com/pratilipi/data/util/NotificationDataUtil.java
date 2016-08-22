package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.I18nGroup;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.RequestParameter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.NotificationData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.I18n;
import com.pratilipi.data.type.Notification;
import com.pratilipi.data.type.User;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.filter.UxModeFilter;

public class NotificationDataUtil {
	
	public static boolean hasAccessToListData( Long userId ) {
		
		return AccessTokenFilter.getAccessToken().getUserId().equals( userId );
		
	}
	
	
	public static List<NotificationData> createNotificationDataList( List<Notification> notificationList )
			throws UnexpectedServerException {
		return createNotificationDataList( notificationList, null );
	}
	
	public static List<NotificationData> createNotificationDataList( List<Notification> notificationList, Language language )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		
		// Pre-fetching required User and Pratilipi Entities
		List<Long> userIdList = new LinkedList<>();
		List<Long> pratilipiIdList = new LinkedList<>();
		for( Notification notification : notificationList ) {
			if( notification.getType() == NotificationType.PRATILIPI_ADD ) {
				pratilipiIdList.add( notification.getSourceIdLong() );
			} else if( notification.getType() == NotificationType.AUTHOR_FOLLOW ) {
				if( notification.getDataIds().size() <= 3 )
					userIdList.addAll( notification.getDataIds() );
				else
					userIdList.addAll( notification.getDataIds().subList( notification.getDataIds().size() - 3, notification.getDataIds().size() ) );
			}
		}
		
		Map<Long, UserData> users = UserDataUtil.createUserDataList( userIdList, true );
		List<PratilipiData> pratilipiDataList = PratilipiDataUtil.createPratilipiDataList( pratilipiIdList, true );
		Map<Long, PratilipiData> pratilipis = new HashMap<>( pratilipiDataList.size() );
		for( PratilipiData pratilipiData : pratilipiDataList )
			pratilipis.put( pratilipiData.getId(), pratilipiData );

		
		// Fetching string translations
		List<I18n> i18nList = dataAccessor.getI18nList( I18nGroup.NOTIFICATION );
		Map<String, I18n> i18ns = new HashMap<>( i18nList.size() );
		for( I18n i18n : i18nList )
			i18ns.put( i18n.getId(), i18n );
		
		
		// Creating Notification Data list
		List<NotificationData> notificationDataList = new ArrayList<>( notificationList.size() );
		for( Notification notification : notificationList ) {
			
			NotificationData notificationData = new NotificationData( notification.getId() );
			notificationData.setUserId( notification.getUserId() );
			
			Language notificationLanguage = language == null
					? users.get( notification.getUserId() ).getAuthor().getLanguage()
					: language;
			if( notificationLanguage == null )
				notificationLanguage = Language.ENGLISH;
			
			if( notification.getType() == NotificationType.PRATILIPI_ADD ) {
				
				PratilipiData pratilipiData = pratilipis.get( notification.getSourceIdLong() );
				if( pratilipiData.getState() ==  PratilipiState.PUBLISHED ) {
					String pratilipiTitle = pratilipiData.getTitle() == null
							? pratilipiData.getTitleEn()
							: pratilipiData.getTitle();
					String authorName = pratilipiData.getAuthor().getName() == null
							? pratilipiData.getAuthor().getNameEn()
							: pratilipiData.getAuthor().getName();
					notificationData.setMessage(
							"<b>" + authorName + "</b> "
							+ i18ns.get( "notification_has_published" ).getI18nString( notificationLanguage )
							+ " <b>" + pratilipiTitle + "</b>" );
					notificationData.setSourceUrl(
							pratilipiData.getPageUrl()
							+ "?" + RequestParameter.NOTIFICATION_ID.getName()
							+ "=" + notification.getId() );
				}
			
			} else 	if( notification.getType() == NotificationType.AUTHOR_FOLLOW ) {
				
				if( notification.getDataIds().size() == 0 ) {
					// Do nothing
				} else if( notification.getDataIds().size() == 1 ) {
					notificationData.setMessage(
							"<b>" + users.get( notification.getDataIds().get( 0 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_has_followed" ).getI18nString( notificationLanguage ) );
				} else if( notification.getDataIds().size() == 2 ) {
					notificationData.setMessage(
							"<b>" + users.get( notification.getDataIds().get( 1 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_and" ).getI18nString( notificationLanguage )
							+ " <b>" + users.get( notification.getDataIds().get( 0 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_have_followed" ).getI18nString( notificationLanguage ) );
				} else if( notification.getDataIds().size() == 3 ) {
					notificationData.setMessage(
							"<b>" + users.get( notification.getDataIds().get( 2 ) ).getDisplayName() + "</b>, "
							+ "<b>" + users.get( notification.getDataIds().get( 1 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_and" ).getI18nString( notificationLanguage )
							+ " <b>" + users.get( notification.getDataIds().get( 0 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_have_followed" ).getI18nString( notificationLanguage ) );
				} else {
					notificationData.setMessage(
							"<b>" + users.get( notification.getDataIds().get( notification.getDataIds().size() - 1 ) ).getDisplayName() + "</b>, "
							+ "<b>" + users.get( notification.getDataIds().get( notification.getDataIds().size() - 2 ) ).getDisplayName() + "</b> "
							+ i18ns.get( "notification_and" ).getI18nString( notificationLanguage )
							+ " " + ( notification.getDataIds().size() - 2 ) + " "
							+ i18ns.get( "notification_others_have_followed" ).getI18nString( notificationLanguage ) );
				}
				
				notificationData.setSourceUrl( "/followers?" + RequestParameter.NOTIFICATION_ID.getName() + "=" + notification.getId() );
			
			}
			
			notificationData.setState( notification.getState() );
			notificationData.setLastUpdatedDate( notification.getLastUpdated() );
			notificationDataList.add( notificationData );
			
		}
		
		
		return notificationDataList;
		
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
		
		
		// Fetching Notification Entities
		DataListCursorTuple<Notification> notificationListCursorTuple =
				dataAccessor.getNotificationList( userId, null, (String) null, cursor, resultCount );
		
		
		// Return
		return new DataListCursorTuple<>(
				createNotificationDataList( notificationListCursorTuple.getDataList(), UxModeFilter.getDisplayLanguage() ),
				notificationListCursorTuple.getCursor() );
		
	}
	
}