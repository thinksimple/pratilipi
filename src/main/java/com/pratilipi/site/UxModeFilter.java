package com.pratilipi.site;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.pratilipi.common.type.Language;

public class UxModeFilter implements Filter {

	private static final ThreadLocal<Language> threadLocalLanguage = new ThreadLocal<Language>();

	
	@Override
	public void init( FilterConfig arg0 ) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
			throws IOException, ServletException {
		
		HttpServletRequest request = ( HttpServletRequest ) req;
		String hostName = request.getServerName();
		
		for( Language language : Language.values() ) {
			if( hostName.equals( language.getHostName() ) ) {
				threadLocalLanguage.set( language );
				break;
			}
		}
			
		chain.doFilter( req, resp );

		threadLocalLanguage.remove();
	}

	
	public static Language getUserLanguage() {
		return threadLocalLanguage.get();
	}

}