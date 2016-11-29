package com.pratilipi.data.type.gae;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.data.type.AuditLog;

@SuppressWarnings("serial")
@Cache
@Entity( name = "AUDIT_LOG" )
public class AuditLogEntity implements AuditLog {

	@Ignore
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapter( Date.class, new JsonSerializer<Date>() {
		
				@Override
				public JsonElement serialize( Date date, Type type, JsonSerializationContext context ) {
					return new JsonPrimitive( date.getTime() );
				}
				
			})
			.registerTypeAdapter( Date.class, new JsonDeserializer<Date>() {
				
				@Override
				public Date deserialize( JsonElement json, Type type, JsonDeserializationContext context ) {
					return new Date( json.getAsLong() );
				}
				
			})
			.create();

	
	@Id
	private Long AUDIT_LOG_ID;
	
	@Index
	private String ACCESS_ID;
	
	@Index
	private AccessType ACCESS_TYPE;

	@Index
	private String PRIMARY_CONTENT_ID;
	
	private String EVENT_DATA_OLD;
	private String EVENT_DATA_NEW;
	private String EVENT_COMMENT;
	
	@Index
	private Date CREATION_DATE;


	public AuditLogEntity() {}
	
	public AuditLogEntity( String accessId, AccessType accessType, Object eventDataOld ) {
		this.ACCESS_ID = accessId;
		this.ACCESS_TYPE = accessType;
		this.EVENT_DATA_OLD = gson.toJson( eventDataOld );
	}
	
	
	@Override
	public Long getId() {
		return AUDIT_LOG_ID;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Key<T> getKey() {
		return getId() == null ? null : (Key<T>) Key.create( getClass(), getId() );
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
	public String getPrimaryContentId() {
		return PRIMARY_CONTENT_ID;
	}

	@Override
	public void setPrimaryContentId( String pageContentId ) {
		this.PRIMARY_CONTENT_ID = pageContentId;
	}

	@Override
	public void setPrimaryContentId( Long pageContentId ) {
		this.PRIMARY_CONTENT_ID = pageContentId.toString();
	}

	@Override
	public String getEventDataOld() {
		return EVENT_DATA_OLD;
	}

	@Override
	public void setEventDataOld( Object eventDataOld ) {
		setEventDataOld( gson.toJson( eventDataOld ) );
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
	public void setEventDataNew( Object eventDataNew ) {
		setEventDataNew( gson.toJson( eventDataNew ) );
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

	@Override
	public void setCreationDate( Date creationDate ) {
		this.CREATION_DATE = creationDate;
	}

}
