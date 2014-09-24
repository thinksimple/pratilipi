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

import com.claymus.commons.server.ClaymusHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;

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
		
		ClaymusHelper claymusHelper = 
				new ClaymusHelper( request ); 
		
		Long currentUser = claymusHelper.getCurrentUserId();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Author author = dataAccessor.getAuthorByUserId( currentUser );
		
		String host = request.getServerName();
		String action = request.getParameter( "action" );
		if( !host.equals( "www.pratilipi.com" )
				&& !host.equals( "devo.pratilipi.com" )
				&& !host.endsWith( ".appspot.com" )
				&& !host.equals( "localhost" )
				&& !host.equals( "127.0.0.1" ) ) {
			
			response.sendRedirect(
					"http://www.pratilipi.com" + request.getRequestURI() );

			PrintWriter out = response.getWriter();
			out.println( "Redirecting to www.pratilipi.com ..." );
			out.close();
			
		} else if ( action != null && action.equals( "login" ) ) {
			if( author != null ) {
				response.sendRedirect( "author/" + author.getId() );
			}
			else
				chain.doFilter( request, response ); 
		}
		else {
			chain.doFilter( request, response );
		}
		
	}

}
