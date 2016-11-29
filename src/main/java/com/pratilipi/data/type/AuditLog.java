package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.AccessType;

public interface AuditLog extends GenericOfyType, Serializable { // TODO: remove Serializable
	
	Long getId();

	String getAccessId();

	void setAccessId( String accessId );
	
	AccessType getAccessType();
	
	void setAccessType( AccessType accessType );

	String getPrimaryContentId();

	void setPrimaryContentId( String pageContentId );
	
	void setPrimaryContentId( Long pageContentId );

	String getEventDataOld();
	
	void setEventDataOld( Object eventDataOld );
	
	@Deprecated
	void setEventDataOld( String eventDataOld );

	String getEventDataNew();

	void setEventDataNew(  Object eventDataNew );
	
	@Deprecated
	void setEventDataNew(  String eventDataNew );

	String getEventComment();
	
	void setEventComment( String eventComment );

	Date getCreationDate();

	void setCreationDate( Date date );
	
}