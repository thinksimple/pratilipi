package com.pratilipi.common.type;

public enum NotificationType {
	
	GENERIC( "NOTIFICATION_GENERIC" ),
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
