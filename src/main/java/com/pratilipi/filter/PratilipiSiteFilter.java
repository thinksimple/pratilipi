package com.pratilipi.filter;

import java.io.IOException;
import java.util.HashMap;
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

import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.PratilipiDataUtil;

public class PratilipiSiteFilter implements Filter {
	
	private final Pattern validHostSubdomainRegEx = Pattern.compile(
			"www\\.(hindi|gujarati|tamil)\\.pratilipi\\.com" );
	private final Pattern validHostRegEx = Pattern.compile(
			"(www|hindi|gujarati|tamil)\\.pratilipi\\.com" // Prod (Standard)
			+ "|"
			+ "(m|hi|gu|ta)\\.pratilipi\\.com" // Prod (Basic)
			+ "|"
			+ "(www|hindi|gujarati|tamil|marathi|malayalam|bengali|kannada|telugu)\\.gamma\\.pratilipi\\.com" // Gamma (Standard)
			+ "|"
			+ "(m|hi|gu|ta|mr|ml|bn|kn|te)\\.gamma\\.pratilipi\\.com" // Gamma (Basic)
			+ "|"
			+ "(www|hindi|gujarati|tamil|marathi|malayalam|bengali)\\.devo-pratilipi\\.appspot\\.com" // Devo (Standard)
			+ "|"
			+ "(m|hi|gu|ta|mr|ml|bn)\\.devo-pratilipi\\.appspot\\.com" // Devo (Basic)
			+ "|"
			+ "(raghu)\\.devo-pratilipi\\.appspot\\.com" // Devo (Raghu)
			+ "|"
			+ "localhost|127.0.0.1" );
	private final Map<String, String> redirections = new HashMap<>();
	private final Pattern oldPratilipiCoverUrlRegEx = Pattern.compile(
			"/resource\\.(book|poem|story|article|pratilipi)-cover/.*"
			+ "|"
			+ "/pratilipi-cover/(150|original)/.*" );
	private final Pattern oldAuthorImageUrlRegEx = Pattern.compile(
			"/resource.author-image/.*"
			+ "|"
			+ "/author-image/150/.*" );
	private final Pattern oldPratilipiReaderUrlRegEx = Pattern.compile( "/read/(book|poem|story|article|pratilipi)/.*" );
	
	{
		redirections.put( "/favicon.ico", "/favicon.png" );
//		redirections.put( "/apple-touch-icon.png", "/theme.pratilipi/favicon.png" );
//		redirections.put( "/apple-touch-icon-precomposed.png", "/theme.pratilipi/favicon.png" );

//		redirections.put( "/robots.txt", "/service.robots" );

		redirections.put( "/give-away", "/" );
		redirections.put( "/give-away/Gora.pdf", "/" );
		redirections.put( "/give-away/Kukurmutta.pdf", "/" );
		redirections.put( "/give-away/Ram_Ki_Shakti_Pooja.pdf", "/" );
		redirections.put( "/give-away/Utkrasht_Sahitya_1.pdf", "/" );
		redirections.put( "/give-away/Chandrakanta.pdf", "/book/5673309542809600" );

		redirections.put( "/event/kk", "/event/kondaadapadaadha-kaadhalgal" );
		redirections.put( "/event/yn", "/event/yaadhumaagi-nindraal" );

		redirections.put( "/about", "/about/pratilipi" );
		redirections.put( "/career", "/JoinTheGang" );
	}
	

	@Override
	public void init( FilterConfig config ) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
				throws IOException, ServletException {

		HttpServletRequest request = ( HttpServletRequest ) req;
		HttpServletResponse response = ( HttpServletResponse ) resp;
		
		String host = request.getServerName();
		String requestUri = request.getRequestURI();
		String userAgent = request.getHeader( "user-agent" );

		
		if( requestUri.equals( "/_ah/warmup" ) || requestUri.equals( "/_ah/start" ) || requestUri.equals( "/_ah/stop" ) ) {
			response.setStatus( HttpServletResponse.SC_NO_CONTENT );
	
			
		} else if( userAgent != null && userAgent.startsWith( "libwww-perl" ) ) {
			response.setStatus( HttpServletResponse.SC_NO_CONTENT );
	
		
		} else if( validHostSubdomainRegEx.matcher( host ).matches() ) { // Redirecting to <lang>.pratilipi.com
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			String queryString = request.getQueryString();
			if( queryString == null || queryString.isEmpty() )
				response.setHeader( "Location", ( request.isSecure() ? "https:" : "http:" ) + "//" + host.substring( 4 ) + requestUri );
			else
				response.setHeader( "Location", ( request.isSecure() ? "https:" : "http:" ) + "//" + host.substring( 4 ) + requestUri + "?" + request.getQueryString() );
			
		
		} else if( !validHostRegEx.matcher( host ).matches() ) { // Redirecting to www.pratilipi.com
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			String queryString = request.getQueryString();
			if( queryString == null || queryString.isEmpty() )
				response.setHeader( "Location", ( request.isSecure() ? "https:" : "http:" ) + "//www.pratilipi.com" + requestUri );
			else
				response.setHeader( "Location", ( request.isSecure() ? "https:" : "http:" ) + "//www.pratilipi.com" + requestUri + "?" + request.getQueryString() );

			
		} else if( requestUri.length() > 1 && requestUri.endsWith( "/" ) ) { // Removing trailing "/"
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", requestUri.substring( 0, requestUri.length() -1 ) );

			
		} else if( redirections.get( requestUri ) != null ) {
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", redirections.get( requestUri ) );

			
		} else if( oldPratilipiCoverUrlRegEx.matcher( requestUri ).matches() ) { // Redirecting to new Pratilipi cover url
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			String pratilipiIdStr = requestUri.indexOf( '?' ) == -1
					? requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 )
					: requestUri.substring( requestUri.lastIndexOf( '/' ) + 1, requestUri.indexOf( '?' ) );
			Long pratilipiId = Long.parseLong( pratilipiIdStr );
			Pratilipi pratilipi = DataAccessorFactory.getDataAccessor().getPratilipi( pratilipiId );
			response.setHeader( "Location", PratilipiDataUtil.createPratilipiCoverUrl( pratilipi, 150 ) );


		} else if( oldAuthorImageUrlRegEx.matcher( requestUri ).matches() ) { // Redirecting to new Author image url
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			String authorIdStr = requestUri.indexOf( '?' ) == -1
					? requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 )
					: requestUri.substring( requestUri.lastIndexOf( '/' ) + 1, requestUri.indexOf( '?' ) );
			Long authorId = Long.parseLong( authorIdStr );
			Author author = DataAccessorFactory.getDataAccessor().getAuthor( authorId );
			response.setHeader( "Location", AuthorDataUtil.createAuthorImageUrl( author, 150 ) );
		
			
		} else if( oldPratilipiReaderUrlRegEx.matcher( requestUri ).matches() ) { // Redirecting to new Pratilipi reader url
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", requestUri.replaceFirst( "/(book|poem|story|article|pratilipi)/", "?id=" ) );

		
		} else {
			chain.doFilter( request, response );
			DataAccessorFactory.destroyDataAccessor();
		}
		
	}

}
