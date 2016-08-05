package com.pratilipi.data.type;

import java.util.Date;
import java.util.List;

import com.pratilipi.common.type.NotificationState;
import com.pratilipi.common.type.NotificationType;


public interface Notification extends GenericOfyType {

	Long getId();
	
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	NotificationType getType();
	
	void setType( NotificationType notificationType );
	
	String getSourceId();
	
	Long getSourceIdLong();
	
	void setSourceId( Long sourceId );
	
	void setSourceId( String sourceId );

	
	List<Long> getDataIds();

	List<Long> getAuditLogIds();
	
	void addDataId( Long dataId, Long auditLogId );
		
	void removeDataId( Long dataId, Long auditLogId );
	
	void addAuditLogId( Long auditLogId );
		
	NotificationState getState();
	
	void setState( NotificationState state );

	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );

	Date getLastUpdated();

	void setLastUpdated( Date lastUpdated );

}