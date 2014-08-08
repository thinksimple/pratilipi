package com.claymus.module.websitewidget.header;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.module.websitewidget.WebsiteWidgetProcessor;
import com.claymus.servlet.ClaymusMain;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HeaderProcessor implements WebsiteWidgetProcessor<Header> {

	private static final Logger logger =
			Logger.getLogger( HeaderProcessor.class.getName() );
	
	private static final String TEMPLATE_NAME
			= "com/claymus/module/websitewidget/header/Header.ftl";
	
	@Override
	public String getHtml( Header header ) {
		Writer writer = new StringWriter();

		try {
			Template template
					= ClaymusMain
							.FREEMARKER_CONFIGURATION
							.getTemplate( TEMPLATE_NAME );
			template.process( header, writer );
		} catch ( IOException | TemplateException e ) {
			logger.log( Level.SEVERE, "Template processing failed.", e );
		}
		
		return writer.toString();
	}

}
