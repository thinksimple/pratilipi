package com.pratilipi.pagecontent.auditlog.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.auditlog.AuditLogContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class AuditLogContentEntity extends PageContentEntity
		implements AuditLogContent {

}
 