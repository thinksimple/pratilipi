package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.data.type.AuditLog;

@PersistenceCapable( table = "AUDIT_LOG" )
public class AuditLogEntity implements AuditLog {

	private static final long serialVersionUID = -2027252778248254540L;

	@PrimaryKey
	@Persistent( column = "AUDIT_LOG_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "ACCESS_ID" )
	private String accessId;
	
	@Persistent( column = "ACCESS_TYPE" )
	private AccessType accessType;

	@Persistent( column = "EVENT_DATA_OLD" )
	private Text eventDataOld;
	
	@Persistent( column = "EVENT_DATA_NEW" )
	private Text eventDataNew;
	
	@Persistent( column = "EVENT_COMMENT" )
	private Text eventComment;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;

	
	@Override
	public Long getId() {
		return id;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Key<T> getKey() {
		return getId() == null ? null : (Key<T>) Key.create( getClass(), getId() );
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.id = key.getId();
	}

	@Override
	public String getAccessId() {
		return accessId;
	}

	@Override
	public void setAccessId( String accessId ) {
		this.accessId = accessId;
	}

	@Override
	public AccessType getAccessType() {
		return accessType;
	}

	@Override
	public void setAccessType( AccessType accessType ) {
		this.accessType = accessType;
	}

	@Override
	public String getEventDataOld() {
		return eventDataOld == null ? null : eventDataOld.getValue();
	}

	@Override
	public void setEventDataOld( Object eventDataOld ) {
		setEventDataOld( new Gson().toJson( eventDataOld ) );
	}
	
	@Override
	public void setEventDataOld( String eventDataOld ) {
		this.eventDataOld = eventDataOld == null ? null : new Text( eventDataOld );
	}

	@Override
	public String getEventDataNew() {
		return eventDataNew == null ? null : eventDataNew.getValue();
	}

	@Override
	public void setEventDataNew( Object eventDataNew ) {
		setEventDataNew( new Gson().toJson( eventDataNew ) );
	}

	@Override
	public void setEventDataNew( String eventDataNew ) {
		this.eventDataNew = eventDataNew == null ? null : new Text( eventDataNew );
	}

	@Override
	public String getEventComment() {
		return eventComment == null ? null : eventComment.getValue();
	}

	@Override
	public void setEventComment( String eventComment ) {
		this.eventComment = eventComment == null ? null : new Text( eventComment );
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

}
