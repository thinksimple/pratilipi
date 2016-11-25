package com.pratilipi.common.type;

import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Notification;

public enum NotificationType {
	
	GENERIC( "NOTIFICATION_GENERIC" ) {
		@Override
		public boolean isValid( Notification notif ) {
			return true;
		}
	},

	
	PRATILIPI( "NOTIFICATION_BOOK" ) {
		@Override
		public boolean isValid( Notification notif ) {
			return DataAccessorFactory.getDataAccessor()
					.getPratilipi( notif.getSourceIdLong() )
					.getState() == PratilipiState.PUBLISHED;
		}
	},

	
	PRATILIPI_ADD( "NOTIFICATION_BOOK" ) {
		@Override
		public boolean isValid( Notification notif ) {
			return DataAccessorFactory.getDataAccessor()
					.getPratilipi( notif.getSourceIdLong() )
					.getState() == PratilipiState.PUBLISHED;
		}
	},
	
	AUTHOR_FOLLOW( "NOTIFICATION_FOLLOWERS" ) {
		@Override
		public boolean isValid( Notification notif ) {
			return ! notif.getDataIds().isEmpty();
		}
	},
	
	;
	
	
	private String androidHandler;
	public abstract boolean isValid( Notification notif );
	
	
	private NotificationType( String androidHandler ) {
		this.androidHandler = androidHandler;
	}
	
	
	public String getAndroidHandler() {
		return androidHandler;
	}
	
}
