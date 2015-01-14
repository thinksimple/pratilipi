package com.pratilipi.pagecontent.auditlog.gae;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.auditlog.AuditLogContent;

@SuppressWarnings("serial")
public class AuditLogContentEntity extends PageContentEntity
		implements AuditLogContent {

	public AuditLogContentEntity( Long auditLogId ) {
		super.setId( auditLogId );
	}
	
}
 