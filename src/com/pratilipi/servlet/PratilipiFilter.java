package com.pratilipi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PratilipiFilter implements Filter {

	@Override
	public void init( FilterConfig config ) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter(
			ServletRequest req, ServletResponse resp, FilterChain chain )
					throws IOException, ServletException {

		HttpServletRequest request = ( HttpServletRequest ) req;
		HttpServletResponse response = ( HttpServletResponse ) resp;

		String host = request.getServerName();
		if( host.equals( "www.pratilipi.com" )
				|| host.equals( "devo.pratilipi.com" )
				|| host.endsWith( ".appspot.com" )
				|| host.equals( "localhost" )
				|| host.equals( "127.0.0.1" ) ) {
			
			chain.doFilter( request, response );
			
		} else {
			response.sendRedirect(
					"http://www.pratilipi.com" + request.getRequestURI() );

			PrintWriter out = response.getWriter();
			out.println( "Redirecting to www.pratilipi.com ..." );
			out.close();
		}
		
	}

}
