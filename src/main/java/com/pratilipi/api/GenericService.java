package com.pratilipi.api;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public abstract class GenericService extends HttpServlet {
	
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
		
			requestUri = "/pratilipi";
			GenericApi api = ApiRegistry.getApi( requestUri );
			api.service( request, response );
			api.service( request, response );
			
		} else {
		
			GenericApi api = ApiRegistry.getApi( requestUri );
			
			if( api == null )
				super.service( request, response );
			else
				api.service( request, response );
		
		}
	}

}
