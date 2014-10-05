package com.claymus.module.pagecontent;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.Memcache;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.UserRole;
import com.claymus.servlet.ClaymusMain;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class PageContentProcessor<T extends PageContent> {

	private static final Logger logger =
			Logger.getLogger( PageContentProcessor.class.getName() );


	protected enum CacheLevel {
		NONE,
		USER,
		USER_ROLE,
		GLOBAL
	}
	
	
	public final String getHtml( T pageContent, HttpServletRequest request )
			throws IOException, UnexpectedServerException {

		Memcache memcache = DataAccessorFactory.getL2CacheAccessor();
		CacheLevel cacheLevel = getCacheLevel();
		String html = null;
		
		if( cacheLevel != CacheLevel.NONE ) {
			ClaymusHelper claymusHelper = ClaymusHelper.get( request );
			String key = getClass().getSimpleName();
			switch( cacheLevel ) {
				case USER:
					key += "-User-" + claymusHelper.getCurrentUserId();
					break;
				case USER_ROLE:
					key += "-UserRole";
					for( UserRole userRole : claymusHelper.getCurrentUserRoleList() )
						key += "-" + userRole.getId();
					break;
				default:
					break;
			}
			key += "-" + pageContent.getLastUpdated().getTime();
			html = memcache.get( key );
			
			if( html == null ) {
				html = generateHtml( pageContent, request );
				memcache.put( key, html );
			}

		} else {
			html = generateHtml( pageContent, request );
		}
		
		return html;
	}

	
	protected CacheLevel getCacheLevel() {
		return CacheLevel.NONE;
	}
	
	
	protected String generateHtml( T pageContent, HttpServletRequest request )
			throws IOException, UnexpectedServerException {
		
		return processTemplate( pageContent, getTemplateName() );
	}
	
	protected String getTemplateName() {
		return null;
	}
	
	protected String processTemplate( Object dataModel, String templateName )
			throws UnexpectedServerException, IOException {
		
		Writer writer = new StringWriter();

		try {
			Template template = ClaymusMain.FREEMARKER_CONFIGURATION
							.getTemplate( templateName );
			template.process( dataModel, writer );
		} catch ( TemplateException e ) {
			logger.log( Level.SEVERE, "Template processing failed.", e );
			throw new UnexpectedServerException();
		}
		
		return writer.toString();
	}
		
}
