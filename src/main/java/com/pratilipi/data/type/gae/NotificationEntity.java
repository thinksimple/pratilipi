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
	
	@Index
	private String SOURCE_ID;

	@Index
	private List<Long> DATA_IDS;
	
	@Index
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

	@Override
	public String getSourceId() {
		return SOURCE_ID;
	}
	
	@Override
	public void setSourceId( Long sourceId ) {
		this.SOURCE_ID = sourceId == null ? null : sourceId.toString();
	}
	
	@Override
	public void setSourceId( String sourceId ) {
		this.SOURCE_ID = sourceId;
	}
	
	@Override
	public List<Long> getDataIds() {
		return DATA_IDS == null ? new ArrayList<Long>( 0 ) : DATA_IDS;
	}

	@Override
	public List<Long> getAuditLogIds() {
		return AUDIT_LOG_IDS == null ? new ArrayList<Long>( 0 ) : AUDIT_LOG_IDS;
	}
	
	@Override
	public void addDataId( Long dataId, Long auditLogId ) {
		
		if( this.DATA_IDS == null ) {
			this.DATA_IDS = new ArrayList<>( 1 );
			this.DATA_IDS.add( dataId );
		} else if( this.DATA_IDS.contains( dataId ) ) {
			// Do Nothing !
		} else {
			this.DATA_IDS.add( dataId );
		}
		
		_addAuditLogId( auditLogId );
		
	}
	
	@Override
	public void removeDataId( Long dataId, Long auditLogId ) {
		
		if( this.DATA_IDS == null ) {
			this.DATA_IDS = new ArrayList<>( 1 );
			this.DATA_IDS.add( dataId );
		} else if( this.DATA_IDS.contains( dataId ) ) {
			this.DATA_IDS.remove( dataId );
		} else {
			// Do Nothing !
		}
		
		_addAuditLogId( auditLogId );
		
	}
	
	private void _addAuditLogId( Long auditLogId ) {
		if( this.AUDIT_LOG_IDS == null ) {
			this.AUDIT_LOG_IDS = new ArrayList<>( 1 );
			this.AUDIT_LOG_IDS.add( auditLogId );
		} else if( this.AUDIT_LOG_IDS.contains( auditLogId ) ) {
			// Do Nothing !
		} else {
			this.AUDIT_LOG_IDS.add( auditLogId );
		}
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
