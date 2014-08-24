package com.claymus.module.pagecontent;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.transfer.PageContent;
import com.claymus.servlet.ClaymusMain;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class PageContentProcessor<T extends PageContent> {

	private static final Logger logger =
			Logger.getLogger( PageContentProcessor.class.getName() );
	
	@Deprecated
	public String getHtml( T pageContent ) {
		
		return processTemplate( pageContent, getTemplateName() );
		
	}
	
	public String getHtml( T pageContent,
			HttpServletRequest request,
			HttpServletResponse response ) {
		
		return processTemplate( pageContent, getTemplateName() );
		
	}
	
	protected String getTemplateName() {
		return null;
	}
	
	protected String processTemplate( Object dataModel, String templateName ) {
		
		Writer writer = new StringWriter();

		try {
			Template template
					= ClaymusMain
							.FREEMARKER_CONFIGURATION
							.getTemplate( templateName );
			template.process( dataModel, writer );
		} catch ( IOException | TemplateException e ) {
			logger.log( Level.SEVERE, "Template processing failed.", e );
		}
		
		return writer.toString();
	}
		
}
