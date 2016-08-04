package com.pratilipi.data.client;

public class NotificationData {

	private Long notificationId;
	
	private String message;
	
	private String sourceUrl;
	
	
	public NotificationData() {}
	
	public NotificationData( Long id ) {
		this.notificationId = id;
	}
	

	public Long getId() {
		return notificationId;
	}

	public void setId( Long id ) {
		this.notificationId = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage( String message ) {
		this.message = message;
	}
	
	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl( String sourceUrl ) {
		this.sourceUrl = sourceUrl;
	}

}
