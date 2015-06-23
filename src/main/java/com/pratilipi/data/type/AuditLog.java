package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.AccessType;

public interface AuditLog extends Serializable {
	
	Long getId();

	String getAccessId();

	void setAccessId( String accessId );
	
	AccessType getAccessType();
	
	void setAccessType( AccessType accessType );

	String getEventDataOld();
	
	void setEventDataOld( String eventDataOld );

	String getEventDataNew();

	void setEventDataNew(  String eventDataNew );

	String getEventComment();
	
	void setEventComment( String eventComment );

	Date getCreationDate();

}