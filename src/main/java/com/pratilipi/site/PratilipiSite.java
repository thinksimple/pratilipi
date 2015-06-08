package com.pratilipi.site;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.FreeMarkerUtil;

@SuppressWarnings("serial")
public class PratilipiSite extends HttpServlet {
	
	private static final String lang = "hi";
	
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		Map<String, String> strMap = new HashMap<String, String>();
		String filePath = System.getProperty( "user.dir" ) + "/WEB-INF/classes/com/pratilipi/site/i18n/language." + lang;
		LineIterator it = FileUtils.lineIterator( new File( filePath ), "UTF-8" );
		try {
			while( it.hasNext() ) {
				String line = it.nextLine();
				if( line.indexOf( '=' ) != -1 )
					strMap.put(
							line.substring( 0, line.indexOf( '=' ) ).trim(),
							line.substring( line.indexOf( '=' ) + 1 ).trim() );
			}
		} finally {
			LineIterator.closeQuietly( it );
		}


		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "_strings", strMap );
		dataModel.put( "lang", lang );
		
		String html = "";
		try {
			html = FreeMarkerUtil.processTemplate( dataModel, "com/pratilipi/site/page/Home.ftl" );
		} catch( UnexpectedServerException e ) {
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			try {
				html = FreeMarkerUtil.processTemplate( dataModel, "com/pratilipi/site/page/error/ServerError.ftl" );
			} catch( UnexpectedServerException ex ) { }
		}
		response.setCharacterEncoding( "UTF-8" );
		response.getWriter().write( html );
		response.getWriter().close();
	}

}