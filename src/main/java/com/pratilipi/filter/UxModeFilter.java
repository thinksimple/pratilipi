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
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;

public class UxModeFilter implements Filter {

	private static final ThreadLocal<Language> threadLocalLanguage = new ThreadLocal<Language>();
	private static final ThreadLocal<Boolean> threadLocalBasicMode = new ThreadLocal<Boolean>();

	
	@Override
	public void init( FilterConfig arg0 ) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
			throws IOException, ServletException {
		
		HttpServletRequest request = ( HttpServletRequest ) req;
		HttpServletResponse response = ( HttpServletResponse ) resp;
		String hostName = request.getServerName();
		String requestUri = request.getRequestURI();
		
		Language language = Language.TAMIL;
		boolean basicMode = true;
		
		for( Language lang : Language.values() ) {
			if( hostName.equals( lang.getHostName() ) ) {
				language = lang;
				basicMode = false;
				break;
			} else if( hostName.equals( lang.getMobileHostName() ) ) {
				language = lang;
				basicMode = true;
				break;
			}
		}
		

		if( language != Language.ENGLISH ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			Page page = dataAccessor.getPage( requestUri );
			if( page != null ) {
				if( page.getType() == PageType.PRATILIPI ) {
					Pratilipi pratilipi = dataAccessor.getPratilipi( page.getPrimaryContentId() );
					if( language != pratilipi.getLanguage() ) {
						response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
						response.setHeader( "Location", ( request.isSecure() ? "https://" : "http://" ) + pratilipi.getLanguage().getHostName() + requestUri );
						return;
					}
				} else if( page.getType() == PageType.AUTHOR ) {
					Author author = dataAccessor.getAuthor( page.getPrimaryContentId() );
					if( language != author.getLanguage() ) {
						response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
						response.setHeader( "Location", ( request.isSecure() ? "https://" : "http://" ) + author.getLanguage().getHostName() + requestUri );
						return;
					}
				}
			}
		}
		
		
		threadLocalLanguage.set( language );
		threadLocalBasicMode.set( basicMode );

		chain.doFilter( req, resp );

		threadLocalLanguage.remove();
		threadLocalBasicMode.remove();
		
	}

	
	public static Language getUserLanguage() {
		return threadLocalLanguage.get();
	}

	public static boolean isBasicMode() {
		return threadLocalBasicMode.get();
	}

}