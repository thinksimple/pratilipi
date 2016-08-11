package com.pratilipi.data.client;

import java.util.Date;

import com.pratilipi.common.type.NotificationState;

public class NotificationData {

	private Long notificationId;
	
	private String message;
	
	private String sourceUrl;
	
	private NotificationState state;

	private Date lastUpdatedDate;


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

	public NotificationState getState() {
		return state;
	}
	
	public void setState( NotificationState state ) {
		this.state = state;
	}
	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate( Date lastUpdatedDate ) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

}
