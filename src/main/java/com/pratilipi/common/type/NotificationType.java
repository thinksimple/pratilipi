package com.pratilipi.common.type;

public enum NotificationType {
	
	PRATILIPI_ADD( "NOTIFICATION_BOOK" ),
	AUTHOR_FOLLOW( "NOTIFICATION_FOLLOWERS" ),
	;
	
	
	private String androidHandler;
	
	
	private NotificationType( String androidHandler ) {
		this.androidHandler = androidHandler;
	}
	
	
	public String getAndroidHandler() {
		return androidHandler;
	}
	
}
