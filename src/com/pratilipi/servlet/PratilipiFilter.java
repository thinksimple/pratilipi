package com.pratilipi.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
	private List<String> nonExistents = new LinkedList<>();
	private List<String> validHosts = new LinkedList<>();
	
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

		redirections.put( "/resource.book-cover/original/4920152667717632", "http://static.pratilipi.com/pratilipi-cover/300/4920152667717632" );
		redirections.put( "/resource.book-cover/original/5710758973276160", "http://static.pratilipi.com/pratilipi-cover/300/5710758973276160" );
		redirections.put( "/resource.book-cover/original/5963192723308544", "http://static.pratilipi.com/pratilipi-cover/300/5963192723308544" );
		redirections.put( "/resource.book-cover/original/6029594259881984", "http://static.pratilipi.com/pratilipi-cover/300/6029594259881984" );
		
		redirections.put( "/resource.book-cover/300/5682462386552832", "http://static.pratilipi.com/pratilipi-cover/300/5682462386552832" );
		redirections.put( "/resource.book-cover/300/6116163083829248", "http://static.pratilipi.com/pratilipi-cover/300/6116163083829248" );
		redirections.put( "/resource.book-cover/300/6248007305527296", "http://static.pratilipi.com/pratilipi-cover/300/6248007305527296" );
		
		redirections.put( "//resource.poem-cover/original/5095141714952192", "http://static.pratilipi.com/pratilipi-cover/300/5095141714952192" );
		redirections.put( "//resource.poem-cover/original/5725485073956864", "http://static.pratilipi.com/pratilipi-cover/300/5725485073956864" );

		redirections.put( "/resource.poem-cover/300/4768179276480512", "http://static.pratilipi.com/pratilipi-cover/300/4768179276480512" );
		redirections.put( "/resource.poem-cover/300/5145057824866304", "http://static.pratilipi.com/pratilipi-cover/300/5145057824866304" );
		redirections.put( "/resource.poem-cover/300/5200909848018944", "http://static.pratilipi.com/pratilipi-cover/300/5200909848018944" );
		redirections.put( "/resource.poem-cover/300/5621792483835904", "http://static.pratilipi.com/pratilipi-cover/300/5621792483835904" );
		redirections.put( "/resource.poem-cover/300/5631639635886080", "http://static.pratilipi.com/pratilipi-cover/300/5631639635886080" );
		redirections.put( "/resource.poem-cover/300/5651757665353728", "http://static.pratilipi.com/pratilipi-cover/300/5651757665353728" );
		redirections.put( "/resource.poem-cover/300/5662197925543936", "http://static.pratilipi.com/pratilipi-cover/300/5662197925543936" );
		redirections.put( "/resource.poem-cover/300/5688737870643200", "http://static.pratilipi.com/pratilipi-cover/300/5688737870643200" );
		redirections.put( "/resource.poem-cover/300/5704726691708928", "http://static.pratilipi.com/pratilipi-cover/300/5704726691708928" );
		redirections.put( "/resource.poem-cover/300/5705718560718848", "http://static.pratilipi.com/pratilipi-cover/300/5705718560718848" );
		redirections.put( "/resource.poem-cover/300/5717648100818944", "http://static.pratilipi.com/pratilipi-cover/300/5717648100818944" );
		redirections.put( "/resource.poem-cover/300/5718156383354880", "http://static.pratilipi.com/pratilipi-cover/300/5718156383354880" );
		
		redirections.put( "/resource.story-cover/original/5684961520648192", "http://static.pratilipi.com/pratilipi-cover/300/5684961520648192" );

		redirections.put( "/resource.story-cover/300/5651498490920960", "http://static.pratilipi.com/pratilipi-cover/300/5651498490920960" );
		redirections.put( "/resource.story-cover/300/5690599873183744", "http://static.pratilipi.com/pratilipi-cover/300/5690599873183744" );
		redirections.put( "/resource.story-cover/300/5726911808405504", "http://static.pratilipi.com/pratilipi-cover/300/5726911808405504" );
		redirections.put( "/resource.story-cover/300/5737350726418432", "http://static.pratilipi.com/pratilipi-cover/300/5737350726418432" );
		
		redirections.put( "/resource.article-cover/300/5154850081865728", "http://static.pratilipi.com/pratilipi-cover/300/5154850081865728" );
		redirections.put( "/resource.article-cover/300/5161516139544576", "http://static.pratilipi.com/pratilipi-cover/300/5161516139544576" );
		redirections.put( "/resource.article-cover/300/5702949112119296", "http://static.pratilipi.com/pratilipi-cover/300/5702949112119296" );
		redirections.put( "/resource.article-cover/300/5724466092965888", "http://static.pratilipi.com/pratilipi-cover/300/5724466092965888" );
	
		nonExistents.add( "/pagecontent.userforms/undefined.cache.js" );
		nonExistents.add( "/pagecontent.pratilipi/undefined.cache.js" );
		nonExistents.add( "/pagecontent.reader/undefined.cache.js" );
		nonExistents.add( "/pagecontent.author/undefined.cache.js" );
	
		validHosts.add( "www.pratilipi.com" );
		validHosts.add( "embed.pratilipi.com" );
//		validHosts.add( "gamma.pratilipi.com" );
		validHosts.add( "legacy.pratilipi.com" );
		validHosts.add( "mark-4p11.prod-pratilipi.appspot.com" );
		validHosts.add( "mark-4p12.prod-pratilipi.appspot.com" );
		validHosts.add( "mark-4p13.prod-pratilipi.appspot.com" );
		validHosts.add( "mark-4p14.prod-pratilipi.appspot.com" );
		validHosts.add( "mark-4p15.prod-pratilipi.appspot.com" );
		validHosts.add( "mark-4p16.prod-pratilipi.appspot.com" );
		validHosts.add( "mark-4p17.prod-pratilipi.appspot.com" );
		validHosts.add( "mark-4p18.prod-pratilipi.appspot.com" );
		validHosts.add( "mark-4p19.prod-pratilipi.appspot.com" );
		validHosts.add( "mark-4p20.prod-pratilipi.appspot.com" );
		validHosts.add( "devo.pratilipi.com" );
		validHosts.add( "alpha.pratilipi.com" );
		validHosts.add( "localhost" );
		validHosts.add( "127.0.0.1" );
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

			
		} else if( requestUri.equals( "/_ah/start" ) || requestUri.equals( "/_ah/stop" ) ) {
			response.setStatus( HttpServletResponse.SC_NO_CONTENT );
			
			
		} else if( requestUri.startsWith( "/static.pratilipi.com/" )
				|| requestUri.startsWith( "/cdn-asia.pratilipi.com/" )
				|| requestUri.startsWith( "/cdn.ckeditor.com/" )
				|| requestUri.startsWith( "/ajax.googleapis.com/" ) ) {
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", ( request.isSecure() ? "https:/" : "http:/" ) + requestUri );
			

		} else if( !validHosts.contains( host ) && !host.endsWith( "devo-pratilipi.appspot.com" ) ) { // Redirecting to www.pratilipi.com
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
