package com.pratilipi.common.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.common.exception.UnexpectedServerException;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;


public class FreeMarkerUtil {

	private static final Logger logger = Logger.getGlobal();

	private static Configuration cfg;

	
	private static Configuration getConfiguration()
			throws UnexpectedServerException {
		
		if( cfg == null ) {
			cfg = new Configuration( Configuration.VERSION_2_3_22 );
			try {
				String templateDir = System.getProperty("user.dir") + "/WEB-INF/classes/";
				cfg.setDirectoryForTemplateLoading( new File( templateDir ) );
			} catch ( IOException e ) {
				logger.log( Level.SEVERE, "Failed to set template directory.", e );
				throw new UnexpectedServerException();
			}
			cfg.setDefaultEncoding( "UTF-8" );
			cfg.setTemplateExceptionHandler( TemplateExceptionHandler.RETHROW_HANDLER );
		}
		return cfg;
	}
	
	public static String processTemplate( Object dataModel, String templateName )
			throws UnexpectedServerException {
		
		Writer writer = new StringWriter();
		processTemplate( dataModel, templateName, writer );
		return writer.toString();
	}

	public static void processTemplate( Object dataModel, String templateName, Writer writer )
			throws UnexpectedServerException {
		
		try {
			getConfiguration().getTemplate( templateName ).process( dataModel, writer );
		} catch ( TemplateException | IOException e ) {
			logger.log( Level.SEVERE, "Template processing failed.", e );
			throw new UnexpectedServerException();
		}
	}

}
