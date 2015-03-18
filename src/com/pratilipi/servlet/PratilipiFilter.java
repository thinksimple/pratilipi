package com.pratilipi.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.pagecontent.blogpost.BlogPostContent;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;

public class PratilipiFilter implements Filter {
	
	private final Map<String, String> redirections = new HashMap<>();
	private final List<String> nonExistents = new LinkedList<>();
	
	private final Pattern oldPratilipiCoverUrlRegEx = Pattern.compile( "/resource\\.(book|poem|story|article|pratilipi)-cover/.*" );
	private final Pattern oldPratilipiReaderUrlRegEx = Pattern.compile( "/read/(book|poem|story|article|pratilipi)/.*" );
	private final Pattern validHostRegEx = Pattern.compile(
			"(www|embed|devo|alpha)\\.pratilipi\\.com"
			+ "|"
			+ "(mark-4p17\\d\\.prod-pratilipi|.+\\.(dev|devo)-pratilipi)\\.appspot\\.com"
			+ "|"
			+ "localhost|127.0.0.1" );
	
	{
		redirections.put( "/favicon.ico", "/theme.pratilipi/favicon.png" );
		redirections.put( "/apple-touch-icon.png", "/theme.pratilipi/favicon.png" );
		redirections.put( "/apple-touch-icon-precomposed.png", "/theme.pratilipi/favicon.png" );

		redirections.put( "/robots.txt", "/service.robots" );

		redirections.put( "/give-away", "/" );
		redirections.put( "/give-away/Gora.pdf", "/" );
		redirections.put( "/give-away/Kukurmutta.pdf", "/" );
		redirections.put( "/give-away/Ram_Ki_Shakti_Pooja.pdf", "/" );
		redirections.put( "/give-away/Utkrasht_Sahitya_1.pdf", "/" );
		redirections.put( "/give-away/Chandrakanta.pdf", "/book/5673309542809600" );

		redirections.put( "/about", "/about/pratilipi" );
		redirections.put( "/career", "/JoinTheGang" );

		nonExistents.add( "/pagecontent.userforms/undefined.cache.js" );
		nonExistents.add( "/pagecontent.pratilipi/undefined.cache.js" );
		nonExistents.add( "/pagecontent.reader/undefined.cache.js" );
		nonExistents.add( "/pagecontent.author/undefined.cache.js" );
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

		
		if( nonExistents.contains( requestUri ) ) {
			response.setStatus( HttpServletResponse.SC_NOT_FOUND );

		
		} else if( redirections.get( requestUri ) != null ) {
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", redirections.get( requestUri ) );

			
		} else if( !validHostRegEx.matcher( host ).matches() ) { // Redirecting to www.pratilipi.com
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			String queryString = request.getQueryString();
			if( queryString == null || queryString.isEmpty() )
				response.setHeader( "Location", "http://www.pratilipi.com" + requestUri );
			else
				response.setHeader( "Location", "http://www.pratilipi.com" + requestUri + "?" + request.getQueryString() );

			
		} else if( oldPratilipiCoverUrlRegEx.matcher( requestUri ).matches() ) { // Redirecting to new Pratilipi cover url
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", requestUri
					.replaceFirst( "/resource.", ( request.isSecure() ? "https:" : "http:" ) + ClaymusHelper.getSystemProperty( "cdn.asia" ) + "/" )
					.replaceFirst( "book|poem|story|article", "pratilipi" )
					.replaceFirst( "original|300", "150" ) );

			
		} else if( oldPratilipiReaderUrlRegEx.matcher( requestUri ).matches() ) { // Redirecting to new Pratilipi reader url
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", requestUri.replaceFirst( "/(book|poem|story|article|pratilipi)/", "?id=" ) );

			
		} else if( requestUri.startsWith( "/cdn-asia.pratilipi.com/" )
				|| requestUri.startsWith( "/static.pratilipi.com/" )
				|| requestUri.startsWith( "/cdn.ckeditor.com/" )
				|| requestUri.startsWith( "/ajax.googleapis.com/" ) ) {
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", ( request.isSecure() ? "https:/" : "http:/" ) + requestUri );
			

		} else if( requestUri.equals( "/_ah/start" ) || requestUri.equals( "/_ah/stop" ) ) {
			response.setStatus( HttpServletResponse.SC_NO_CONTENT );
			
			
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
