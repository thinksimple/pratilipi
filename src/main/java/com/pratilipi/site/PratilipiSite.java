package com.pratilipi.site;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.FreeMarkerUtil;
import com.pratilipi.common.util.LanguageUtil;

@SuppressWarnings("serial")
public class PratilipiSite extends HttpServlet {
	
	private static final String lang = "hi";
	private static final String defaulLang = "en";
	private static final String languageFilePrefix = "WEB-INF/classes/com/pratilipi/site/i18n/language.";
	
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "_strings", LanguageUtil.getStrings( languageFilePrefix + lang, languageFilePrefix + defaulLang ) );
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