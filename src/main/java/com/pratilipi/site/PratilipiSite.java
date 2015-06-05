package com.pratilipi.site;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.FreeMarkerUtil;

@SuppressWarnings("serial")
public class PratilipiSite extends HttpServlet {
	
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		Properties defaultProps = new Properties();
		InputStream in = this.getClass().getResourceAsStream( "i18n/strings.en" );
		defaultProps.load(in);
		in.close();
	
		Map<String, String> strMap = new HashMap<String, String>();
		for( Entry<Object, Object> entry : defaultProps.entrySet() )
			strMap.put( (String)entry.getKey(), (String)entry.getValue() );

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("_strings", strMap);
		
		String html = "";
		try {
			html = FreeMarkerUtil.processTemplate( dataModel, "com/pratilipi/site/page/Home.ftl" );
		} catch( UnexpectedServerException e ) {
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			try {
				html = FreeMarkerUtil.processTemplate( dataModel, "com/pratilipi/site/page/error/ServerError.ftl" );
			} catch( UnexpectedServerException ex ) { }
		}
		response.getWriter().write( html );
		response.getWriter().close();
	}

}