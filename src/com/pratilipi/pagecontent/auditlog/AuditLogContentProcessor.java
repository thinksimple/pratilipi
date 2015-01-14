package com.pratilipi.pagecontent.auditlog;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;

public class AuditLogContentProcessor extends PageContentProcessor<AuditLogContent> {

	@Override
	public String generateTitle( AuditLogContent auditLogContent, HttpServletRequest request ) {
		return null;
	}
	
	@Override
	public String generateHtml(
			AuditLogContent auditLogContent,
			HttpServletRequest request ) throws UnexpectedServerException {
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		
		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
