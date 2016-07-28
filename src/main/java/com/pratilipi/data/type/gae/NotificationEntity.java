package com.pratilipi.data.type.gae;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.pratilipi.common.type.NotificationState;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.data.type.Notification;

@Cache
@Entity( name = "NOTIFICATION" )
public class NotificationEntity implements Notification {

	@Id
	private Long NOTIFICATION_ID;
	
	
	@Index
	private Long USER_ID;
	
	@Index
	private NotificationType TYPE;

	private Object DATA;
	
	private List<Long> AUDIT_LOG_IDS;
	
	@Index
	private NotificationState STATE;
	
	
	@Index
	private Date CREATION_DATE;
	
	@Index
	private Date LAST_UPDATED;



	public NotificationEntity() {}

	public NotificationEntity( Long id ) {
		this.NOTIFICATION_ID = id;
	}

	
	@Override
	public Long getId() {
		return NOTIFICATION_ID;
	}
	
	public void setId( Long id ) {
		this.NOTIFICATION_ID = id;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Key<T> getKey() {
		return getId() == null ? null : (Key<T>) Key.create( getClass(), getId() );
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.NOTIFICATION_ID = key.getId();
	}
	
	
	@Override
	public Long getUserId() {
		return USER_ID;
	}

	@Override
	public void setUserId( Long userId ) {
		this.USER_ID = userId;
	}

	@Override
	public NotificationType getType() {
		return TYPE;
	}

	@Override
	public void setType( NotificationType type ) {
		this.TYPE = type;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getData() {
		return (T) DATA;
	}

	@Override
	public void setData( Object data ) {
		this.DATA = data;
	}
	
	@Override
	public List<Long> getAuditLogIds() {
		return AUDIT_LOG_IDS;
	}
	
	@Override
	public void addAuditLogId( Long auditLogId ) {
		if( this.AUDIT_LOG_IDS == null )
			this.AUDIT_LOG_IDS = new ArrayList<>( 1 );
		this.AUDIT_LOG_IDS.add( auditLogId );
	}
	
	@Override
	public NotificationState getState() {
		return STATE;
	}

	@Override
	public void setState( NotificationState state ) {
		this.STATE = state;
	}
	
	
	@Override
	public Date getCreationDate() {
		return CREATION_DATE;
	}
	
	@Override
	public void setCreationDate( Date creationDate ) {
		this.CREATION_DATE = creationDate;
	}

	@Override
	public Date getLastUpdated() {
		return LAST_UPDATED;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.LAST_UPDATED = lastUpdated;
	}

}
