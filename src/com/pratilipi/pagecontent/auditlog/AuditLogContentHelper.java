package com.pratilipi.pagecontent.auditlog;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.data.transfer.AuditLog;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.auditlog.gae.AuditLogContentEntity;
import com.pratilipi.pagecontent.author.shared.AuthorContentData;

public class AuditLogContentHelper extends PageContentHelper<
		AuditLogContent,
		AuthorContentData,
		AuditLogContentProcessor> {
	
	public static final Access ACCESS_TO_READ_AUDITLOG_CONTENT =
			new Access( "auditlog_data_read_content", false, "View Audit Log Content" );
	
	
	@Override
	public String getModuleName() {
		return "AuditLog";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_READ_AUDITLOG_CONTENT
		};
	}
	
	public static AuditLogContent newAuditLogContent() {
		return new AuditLogContentEntity();
	}
	
	public static boolean hasRequestAccessToReadAuditLogContent( HttpServletRequest request, AuditLog auditLog ) {
		return false;
	}	

}
