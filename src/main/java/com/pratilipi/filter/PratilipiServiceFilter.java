package com.pratilipi.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratilipi.data.DataAccessorFactory;

public class PratilipiServiceFilter implements Filter {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiServiceFilter.class.getName() );

	
	@Override
	public void init( FilterConfig config ) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
				throws IOException, ServletException {

		HttpServletRequest request = ( HttpServletRequest ) req;
		HttpServletResponse response = ( HttpServletResponse ) resp;
		
		String requestUri = request.getRequestURI();

		if( requestUri.equals( "/_ah/start" ) || requestUri.equals( "/_ah/stop" ) ) {
			response.setStatus( HttpServletResponse.SC_NO_CONTENT );
		} else if( requestUri.equals( "/api" ) ) {
			DataAccessorFactory.getL1CacheAccessor().flush();
			logger.log( Level.WARNING, requestUri );
			req.getRequestDispatcher("/pratilipi?pratilipiId=5734670612299776").forward( req, resp );
			req.getRequestDispatcher("/pratilipi?pratilipiId=5734670612299777").forward( req, resp );
		} else {
			DataAccessorFactory.getL1CacheAccessor().flush();
			chain.doFilter( request, response );
		}
		
	}

}
