package com.pratilipi.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.pagecontent.blogpost.BlogPostContent;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;

public class PratilipiFilter implements Filter {
	
	private Map<String, String> redirections = new HashMap<>();
	
	{
		redirections.put( "/favicon.ico", "/theme.pratilipi/favicon.png" );
		redirections.put( "/apple-touch-icon.png", "/theme.pratilipi/favicon.png" );
		redirections.put( "/apple-touch-icon-precomposed.png", "/theme.pratilipi/favicon.png" );

		redirections.put( "/give-away", "/" );
		redirections.put( "/give-away/Gora.pdf", "/" );
		redirections.put( "/give-away/Kukurmutta.pdf", "/" );
		redirections.put( "/give-away/Ram_Ki_Shakti_Pooja.pdf", "/" );
		redirections.put( "/give-away/Utkrasht_Sahitya_1.pdf", "/" );
		redirections.put( "/give-away/Chandrakanta.pdf", "/book/5673309542809600" );

		redirections.put( "/about", "/about/pratilipi" );

		redirections.put( "/resource.book-cover/original/5710758973276160", "http://static.pratilipi.com/pratilipi-cover/300/5710758973276160" );
		redirections.put( "/resource.book-cover/300/6248007305527296", "http://static.pratilipi.com/pratilipi-cover/300/6248007305527296" );
		redirections.put( "/resource.poem-cover/300/5704726691708928", "http://static.pratilipi.com/pratilipi-cover/300/5704726691708928" );
		redirections.put( "/resource.poem-cover/300/5705718560718848", "http://static.pratilipi.com/pratilipi-cover/300/5705718560718848" );
		redirections.put( "/resource.story-cover/300/5690599873183744", "http://static.pratilipi.com/pratilipi-cover/300/5690599873183744" );
	}
	

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
		String requestUri = request.getRequestURI();
		String action = request.getParameter( "action" );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		
		if( redirections.get( requestUri ) != null ){
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", redirections.get( requestUri ) );

			
		} else if( !host.equals( "www.pratilipi.com" )
				&& !host.equals( "gamma.pratilipi.com" )
				&& !host.equals( "mark-3p17.prod-pratilipi.appspot.com" )
				&& !host.equals( "devo.pratilipi.com" )
				&& !host.endsWith( "devo-pratilipi.appspot.com" )
				&& !host.equals( "localhost" )
				&& !host.equals( "127.0.0.1" ) ) { // Redirecting to www.pratilipi.com
			
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", "http://www.pratilipi.com" + requestUri );

			
		} else if( action != null && action.equals( "login" ) ) { // Redirecting to profile page on login
			
			PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );

			Long currentUserId = pratilipiHelper.getCurrentUserId();
			Author author = dataAccessor.getAuthorByUserId( currentUserId );
			
			if( author != null )
				response.sendRedirect( PratilipiPageType.AUTHOR.getUrlPrefix() + author.getId() );
			else
				chain.doFilter( request, response );
		
			
		} else if( requestUri.startsWith( "/blog/" ) ) { // Redirecting author interviews to /author-interview/*
			String blogIdStr = requestUri.substring( 6 );
			if( ! blogIdStr.equals( "new" ) ) {
				BlogPostContent blogPostContent = (BlogPostContent) dataAccessor.getPageContent( Long.parseLong( blogIdStr ) );
				if( blogPostContent.getBlogId() == 5197509039226880L ) {
					response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
					response.setHeader( "Location", "/author-interview/" + blogIdStr );
				} else {
					chain.doFilter( request, response );
				}
			} else {
				chain.doFilter( request, response );
			}
			
			
		} else {
			chain.doFilter( request, response );
		}

		
		dataAccessor.destroy();
	}

}
