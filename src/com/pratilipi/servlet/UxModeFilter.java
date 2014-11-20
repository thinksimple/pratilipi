package com.pratilipi.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.transfer.Page;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;

public class UxModeFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = ( HttpServletRequest ) req;
		String userAgent = request.getHeader( "user-agent" ).toLowerCase();
		
		boolean basicMode = true;
		
//		if( userAgent.contains( "opera" ) ) // Opera Mini
//			basicMode = false;
//		else if ( userAgent.contains( "opr" ) ) // Opera
//			basicMode = false;
//		else if ( userAgent.contains( "ucbrowser" ) ) // UC Browser
//			basicMode = false;
//		else if ( userAgent.contains( "msie" ) ) // Microsoft Internet Explorer
//			basicMode = false;
//		else if ( userAgent.contains( "firefox" ) ) // Mozilla Firfox
//			basicMode = false;
		if( userAgent.contains( "chrome" ) ) // Google Chrome
			basicMode = false;
//		else if ( userAgent.contains( "safari" ) ) // Apple Safari
//			basicMode = false;
//		else if ( userAgent.contains( "gecko" ) ) // Gecko Family Browsers
//			basicMode = false;
		
		if( !basicMode ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
			Page page = dataAccessor.getPage( request.getRequestURI() );
			basicMode = page == null
					|| !page.getType().equals( PratilipiPageType.READ.toString() );
		}
		
		request.setAttribute( ClaymusHelper.REQUEST_ATTRIB_MODE_BASIC, basicMode );
		
		chain.doFilter( req, resp );
	}
	
	@Override
	public void destroy() { }

	@Override
	public void init(FilterConfig arg0) throws ServletException { }

}
