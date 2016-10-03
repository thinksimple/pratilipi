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
			HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException {

		String requestUri = request.getRequestURI();
		
		ServletConfig servletConfig = getServletConfig();
		String prefix = servletConfig.getInitParameter( "Prefix" );
		if( prefix != null && ! prefix.isEmpty() )
			requestUri = requestUri.substring( prefix.length() );

		if( requestUri.equals( "/" ) || requestUri.isEmpty() ) {
			
			logger.log( Level.WARNING, request.getRequestURL().toString() );
			
			String[] reqUris = request.getParameter( "req" ).split( ";" );
			for( final String reqUri : reqUris ) {
				if( reqUri.trim().isEmpty() )
					continue;
				logger.log( Level.WARNING, reqUri.trim() );
				HttpServletRequestWrapper req = new HttpServletRequestWrapper( request ) {
/*					public String getQueryString() {
						return reqUri.substring( reqUri.indexOf( '?' ) + 1 );
					}
					public String getRequestURI() {
						return reqUri.substring( 0, reqUri.indexOf( '?' ) );
					}*/
					public StringBuffer getRequestURL() {
						return new StringBuffer( reqUri.trim() );
					}
				};	
				logger.log( Level.WARNING, req.getQueryString() );
				logger.log( Level.WARNING, req.getRequestURI() );
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
