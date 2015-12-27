package com.pratilipi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.Website;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;

public class UxModeFilter implements Filter {

	private static boolean isAndroidApp = false;
	
	private static final ThreadLocal<Boolean> threadLocalBasicMode = new ThreadLocal<Boolean>();
	
	private static final ThreadLocal<Language> threadLocalDisplayLanguage = new ThreadLocal<Language>();
	private static final ThreadLocal<Language> threadLocalFilterLanguage = new ThreadLocal<Language>();

	
	@Override
	public void init( FilterConfig config ) throws ServletException {
		String moduleParam = config.getInitParameter( "Module" );
		isAndroidApp = moduleParam != null && moduleParam.equalsIgnoreCase( "Android" );
	}

	@Override
	public void destroy() { }

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
			throws IOException, ServletException {
		
		if( isAndroidApp ) {

			threadLocalBasicMode.set( false );
			threadLocalDisplayLanguage.set( Language.ENGLISH );
			threadLocalFilterLanguage.set( null );
		
		} else {
		
			HttpServletRequest request = ( HttpServletRequest ) req;
			HttpServletResponse response = ( HttpServletResponse ) resp;
			String hostName = request.getServerName();
			String requestUri = request.getRequestURI();
	
			// Defaults - for all test environments
			boolean basicMode = false;
			Language displayLanguage = Language.TAMIL;
			Language filterLanguage = Language.TAMIL;
	
			for( Website website : Website.values() ) {
				if( hostName.equals( website.getHostName() ) ) {
					basicMode = false;
					displayLanguage = website.getDisplayLanguage();
					filterLanguage = website.getFilterLanguage();
					break;
				} else if( hostName.equals( website.getMobileHostName() ) ) {
					basicMode = true;
					displayLanguage = website.getDisplayLanguage();
					filterLanguage = website.getFilterLanguage();
					break;
				}
			}
			
	
			if( filterLanguage != null ) {
				DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
				Page page = dataAccessor.getPage( requestUri );
				if( page != null ) {
					if( page.getType() == PageType.PRATILIPI ) {
						Pratilipi pratilipi = dataAccessor.getPratilipi( page.getPrimaryContentId() );
						if( filterLanguage != pratilipi.getLanguage() ) {
							response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
							response.setHeader( "Location", ( request.isSecure() ? "https://" : "http://" ) + Website.ALL_LANGUAGE.getHostName() + requestUri );
							return;
						}
					} else if( page.getType() == PageType.AUTHOR ) {
						Author author = dataAccessor.getAuthor( page.getPrimaryContentId() );
						if( filterLanguage != author.getLanguage() ) {
							response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
							response.setHeader( "Location", ( request.isSecure() ? "https://" : "http://" ) + Website.ALL_LANGUAGE.getHostName() + requestUri );
							return;
						}
					}
				}
			}
			
			
			threadLocalBasicMode.set( basicMode );
			threadLocalDisplayLanguage.set( displayLanguage );
			threadLocalFilterLanguage.set( filterLanguage );

		}
		
		chain.doFilter( req, resp );

		threadLocalBasicMode.remove();
		threadLocalDisplayLanguage.remove();
		threadLocalFilterLanguage.remove();
		
	}

	public static boolean isAndroidApp() {
		return isAndroidApp;
	}

	public static boolean isBasicMode() {
		return threadLocalBasicMode.get();
	}

	@Deprecated
	public static Language getUserLanguage() {
		return threadLocalDisplayLanguage.get();
	}

	public static Language getDisplayLanguage() {
		return threadLocalDisplayLanguage.get();
	}

	public static Language getFilterLanguage() {
		return threadLocalFilterLanguage.get();
	}

}