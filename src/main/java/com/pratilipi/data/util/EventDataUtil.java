package com.pratilipi.data.util;

import java.util.logging.Logger;

import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.client.EventData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.Event;
import com.pratilipi.filter.AccessTokenFilter;


public class EventDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( EventDataUtil.class.getName() );

	
	private static final String BANNER_FOLDER = "event-banner/original";


	public static boolean hasAccessToAddEventData( EventData eventData ) {
		
		// User with EVENT_ADD access can add an Event.
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), eventData.getLanguage(), AccessType.EVENT_ADD ) )
			return true;
		
		return false;
		
	}

	public static boolean hasAccessToUpdatePratilipiData( Event event, EventData eventData ) {

		// User with EVENT_UPDATE access can update any event.
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), event.getLanguage(), AccessType.EVENT_UPDATE ) ) {
			if( eventData == null || ! eventData.hasLanguage() || eventData.getLanguage() == event.getLanguage() )
				return true;
			else if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), eventData.getLanguage(), AccessType.EVENT_UPDATE ) )
				return true;
		}
		
		return false;
		
	}
	
	
	public static String createEventBannerUrl( Event event ) {
		return "/event/banner" + "?eventId=" + event.getId() + "&version=" + event.getLastUpdated().getTime();
	}

	
	public static EventData createEventData( Event event ) {
		return null; // TODO: Implementation
	}
	
	public static EventData saveEventData( EventData event ) {
		return null; // TODO: Implementation
	}
	
}
