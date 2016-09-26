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

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.common.type.PageType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Page;


public class LogAndSecurityFilter implements Filter {

	private static final Logger logger = 
			Logger.getLogger( LogAndSecurityFilter.class.getName() ); 

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException {	}

	@Override
	public void destroy() {	}

	@Override
	public void doFilter( ServletRequest request, ServletResponse response,
			FilterChain chain ) throws IOException, ServletException {

		String uri = ( ( HttpServletRequest ) request ).getRequestURI().toString();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page page = dataAccessor.getPage( uri );
		if( page != null ) {
			if( page.getType() == PageType.BLOG_POST ) {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty( "PAGE_ID", page.getId() );
				jsonObject.addProperty( "ACCESS_TOKEN", AccessTokenFilter.getAccessToken().getId() );
				logger.log( Level.INFO, "PAGE_HIT :: " + new Gson().toJson( jsonObject ) );
			}
		}

		chain.doFilter( request, response );

	}

}
