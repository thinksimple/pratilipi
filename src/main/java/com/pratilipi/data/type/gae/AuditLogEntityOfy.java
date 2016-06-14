package com.pratilipi.data.type.gae;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.data.type.AuditLog;

@SuppressWarnings("serial")
@Cache
@Entity( name = "AUDIT_LOG" )
public class AuditLogEntityOfy implements AuditLog {
	
	@Id
	private Long AUDIT_LOG_ID;
	
	@Index
	private String ACCESS_ID;
	
	@Index
	private AccessType ACCESS_TYPE;

	private String EVENT_DATA_OLD;
	private String EVENT_DATA_NEW;
	private String EVENT_COMMENT;
	
	@Index
	private Date CREATION_DATE;

	
	@Override
	public Long getId() {
		return AUDIT_LOG_ID;
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.AUDIT_LOG_ID = key.getId();
	}

	@Override
	public String getAccessId() {
		return ACCESS_ID;
	}

	@Override
	public void setAccessId( String accessId ) {
		this.ACCESS_ID = accessId;
	}

	@Override
	public AccessType getAccessType() {
		return ACCESS_TYPE;
	}

	@Override
	public void setAccessType( AccessType accessType ) {
		this.ACCESS_TYPE = accessType;
	}

	@Override
	public String getEventDataOld() {
		return EVENT_DATA_OLD;
	}

	@Override
	public void setEventDataOld( String eventDataOld ) {
		this.EVENT_DATA_OLD = eventDataOld;
	}

	@Override
	public String getEventDataNew() {
		return EVENT_DATA_NEW;
	}

	@Override
	public void setEventDataNew( String eventDataNew ) {
		this.EVENT_DATA_NEW = eventDataNew;
	}

	@Override
	public String getEventComment() {
		return EVENT_COMMENT;
	}

	@Override
	public void setEventComment( String eventComment ) {
		this.EVENT_COMMENT = eventComment;
	}

	@Override
	public Date getCreationDate() {
		return CREATION_DATE;
	}

	public void setCreationDate( Date creationDate ) {
		this.CREATION_DATE = creationDate;
	}

}
