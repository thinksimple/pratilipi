package com.pratilipi.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

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
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				HttpServletResponseWrapper resp = new HttpServletResponseWrapper( response ) {
					@Override
					public PrintWriter getWriter() throws IOException {
						return new PrintWriter( baos );
					}
				};
				logger.log( Level.WARNING, reqUri.trim() );
				request.getRequestDispatcher( reqUri.trim() ).forward( request, resp );
				response.getOutputStream().write( baos.toByteArray() );
			}
			response.getWriter().close();
			
		} else {
		
			logger.log( Level.SEVERE, requestUri );
			GenericApi api = ApiRegistry.getApi( requestUri );
			
			if( api == null )
				super.service( request, response );
			else
				api.service( request, response );
		
		}
	}

}
