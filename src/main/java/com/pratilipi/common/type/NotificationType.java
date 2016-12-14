package com.pratilipi.common.type;

import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Notification;

public enum NotificationType {
	
	GENERIC( true, "NOTIFICATION_GENERIC" ) {
		@Override
		public boolean isValid( Notification notif ) {
			return true;
		}
	},

	
	PRATILIPI( true, "NOTIFICATION_BOOK" ) {
		@Override
		public boolean isValid( Notification notif ) {
			return DataAccessorFactory.getDataAccessor()
					.getPratilipi( notif.getSourceIdLong() )
					.getState() == PratilipiState.PUBLISHED;
		}
	},

	
	PRATILIPI_PUBLISHED_AUTHOR( true, "NOTIFICATION_BOOK" ) { // Notification sent to the Author when a Pratilipi is published
		@Override
		public boolean isValid( Notification notif ) {
			return DataAccessorFactory.getDataAccessor()
					.getPratilipi( notif.getSourceIdLong() )
					.getState() == PratilipiState.PUBLISHED;
		}
	},

	PRATILIPI_PUBLISHED_FOLLOWER( true, "NOTIFICATION_BOOK" ) { // Notification sent to Author's followers when a Pratilipi is published
		@Override
		public boolean isValid( Notification notif ) {
			return DataAccessorFactory.getDataAccessor()
					.getPratilipi( notif.getSourceIdLong() )
					.getState() == PratilipiState.PUBLISHED;
		}
	},

	
	AUTHOR_FOLLOW( true, "NOTIFICATION_FOLLOWERS" ) {
		@Override
		public boolean isValid( Notification notif ) {
			return ! notif.getDataIds().isEmpty();
		}
	},
	
	
	USER_PRATILIPI_REVIEW( true, "NOTIFICATION_REVIEW" ) {
		@Override
		public boolean isValid( Notification notif ) {
			return DataAccessorFactory.getDataAccessor()
					.getUserPratilipi( notif.getSourceId() )
					.getReviewState() == UserReviewState.PUBLISHED;
		}
	},

	;
	
	
	
	private boolean subscriptionDefault;
	private String androidHandler;
	
	public abstract boolean isValid( Notification notif );
	
	
	private NotificationType( boolean subscriptionDefault, String androidHandler ) {
		this.subscriptionDefault = subscriptionDefault;
		this.androidHandler = androidHandler;
	}
	

	public boolean getSubscriptionDefault() {
		return subscriptionDefault;
	}
	
	public String getAndroidHandler() {
		return androidHandler;
	}
	
}
