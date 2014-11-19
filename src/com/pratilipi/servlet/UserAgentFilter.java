package com.pratilipi.servlet;

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

public class UserAgentFilter implements Filter {
	
	private static final Logger logger = 
			Logger.getLogger( UserAgentFilter.class.getName() );

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = ( HttpServletRequest ) req;
		HttpServletResponse response = ( HttpServletResponse ) resp;
		
		String userAgent = request.getHeader( "user-agent" ).toLowerCase();
		
		String browserName = "";
		
		if( userAgent.contains( "opera" ))
			browserName = "opera mini";
		else if ( userAgent.contains( "opr" ) )
			browserName = "opera";
		else if ( userAgent.contains( "ucbrowser" ) )
			browserName = "ucbrowser";
		else if ( userAgent.contains( "msie" ) )
			browserName = "ie";
		else if ( userAgent.contains( "firefox" ))
			browserName = "firefox";
		else if ( userAgent.contains( "chrome" ))
			browserName = "chrome";
		else if ( userAgent.contains( "safari" ))
			browserName = "safari";
		else if ( userAgent.contains( "gecko" ) )
			browserName = "gecko";
		
		//TODO : SET BASIC ATTRIBUTE BASED ON POLYMER SUPPORT ( AS OF NOW ).
		boolean basic;
		if( browserName.equals( "firefox" ) || 
				browserName.equals( "safari" ) ||
				browserName.equals( "chrome" ) ||
				browserName.equals( "gecko" ) )
			basic = false;
		else 
			basic = true;
		
		request.setAttribute( "basic", basic );
		
		logger.log( Level.INFO, "Browser : " + browserName );
		
		chain.doFilter( request, response );
		
	}
	
	@Override
	public void destroy() { }

	@Override
	public void init(FilterConfig arg0) throws ServletException { }
	
	

}
