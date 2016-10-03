package com.pratilipi.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public abstract class GenericService extends HttpServlet {
	
	private static final Logger logger =
			Logger.getLogger( GenericApi.class.getName() );
	
	static { }
	
	@Override
	protected final void service(
			final HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException {

		String requestUri = request.getRequestURI();
		
		ServletConfig servletConfig = getServletConfig();
		String prefix = servletConfig.getInitParameter( "Prefix" );
		if( prefix != null && ! prefix.isEmpty() )
			requestUri = requestUri.substring( prefix.length() );

		if( requestUri.equals( "/" ) || requestUri.isEmpty() ) {
			
			String[] reqUris = request.getParameter( "req" ).split( ";" );
			for( final String reqUri : reqUris ) {
				if( reqUri.trim().isEmpty() )
					continue;
				logger.log( Level.WARNING, reqUri.trim() );
				HttpServletRequestWrapper req = new HttpServletRequestWrapper( request ) {
					@Override
					public String getQueryString() {
						return reqUri.substring( reqUri.indexOf( '?' ) + 1 );
					}
					@Override
					public String getRequestURI() {
						return reqUri.substring( 0, reqUri.indexOf( '?' ) );
					}
					@Override
					public StringBuffer getRequestURL() {
						String url = request.getRequestURL().toString();
						url = url.substring( 0, url.indexOf( '/' ) );
						url = url + reqUri;
						return new StringBuffer( reqUri );
					}
				};	
				ApiRegistry.getApi( req.getRequestURI() ).service( request, response );
			}
			
		} else {
		
			GenericApi api = ApiRegistry.getApi( requestUri );
			
			if( api == null )
				super.service( request, response );
			else
				api.service( request, response );
		
		}
	}

}
