package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.NotificationState;
import com.pratilipi.common.type.NotificationType;


public interface Notification extends GenericOfyType {

	Long getId();
	
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	NotificationType getType();
	
	void setType( NotificationType notificationType );
	
	<T> T getData();
	
	void setData( Object data );

	NotificationState getState();
	
	void setState( NotificationState state );

	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );

	Date getLastUpdated();

	void setLastUpdated( Date lastUpdated );

}