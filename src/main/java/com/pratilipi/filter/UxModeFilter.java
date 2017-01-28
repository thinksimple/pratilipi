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

	private static boolean isWebApp = false;
	private static boolean isAndroidApp = false;
	
	private static final ThreadLocal<Boolean> threadLocalBasicMode = new ThreadLocal<Boolean>();
	private static final ThreadLocal<Website> threadLocalWebsite = new ThreadLocal<Website>();

	
	@Override
	public void init( FilterConfig config ) throws ServletException {
		String moduleParam = config.getInitParameter( "Module" );
		isWebApp = moduleParam != null && moduleParam.equalsIgnoreCase( "WebApp" );
		isAndroidApp = moduleParam != null && moduleParam.equalsIgnoreCase( "Android" );
	}

	@Override
	public void destroy() { }

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
			throws IOException, ServletException {
		
		if( isAndroidApp ) {

			threadLocalBasicMode.set( false );
			threadLocalWebsite.set( null );
		
		} else {
		
			HttpServletRequest request = ( HttpServletRequest ) req;
			HttpServletResponse response = ( HttpServletResponse ) resp;
			String hostName = request.getServerName();
			String requestUri = request.getRequestURI();
			String userAgent = request.getHeader( "user-agent" );
			
			
			// Defaults - for all test environments
			boolean basicMode = false;
			Website website = null;

			// Figuring out Mode and Languages from a pre-configured list
			for( Website web : Website.values() ) {
				if( hostName.equals( web.getHostName() ) ) {
					basicMode = false;
					website = web;
					break;
				} else if( hostName.equals( web.getMobileHostName() ) ) {
					basicMode = true;
					website = web;
					break;
				}
			}
			
	
			// Redirect Pratilipi & Author page requests on www.pratilipi.com to language specific Websites
			// NOTE: DO NOT redirect Facebook Scraping requests
			if( isWebApp && website == Website.ALL_LANGUAGE && ( userAgent == null || userAgent.isEmpty() || ! userAgent.startsWith( "facebookexternalhit/1.1" ) ) ) {
				
				String destHostName = null;
				
				DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
				Page page = dataAccessor.getPage( requestUri );
				
				if( page == null ) {
					// Do Nothing
					
				} else if( page.getType() == PageType.PRATILIPI ) {
					Pratilipi pratilipi = dataAccessor.getPratilipi( page.getPrimaryContentId() );
					for( Website web : Website.values() ) {
						if( web.getFilterLanguage() == pratilipi.getLanguage() ) {
							destHostName = basicMode ? web.getMobileHostName() : web.getHostName();
							break;
						}
					}
				
				} else if( page.getType() == PageType.AUTHOR ) {
					Author author = dataAccessor.getAuthor( page.getPrimaryContentId() );
					for( Website web : Website.values() ) {
						if( web.getFilterLanguage() == author.getLanguage() ) {
							destHostName = basicMode ? web.getMobileHostName() : web.getHostName();
							break;
						}
					}
				
				}
			
				if( destHostName != null ) {
					response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
					response.setHeader( "Location", ( request.isSecure() ? "https://" : "http://" ) + destHostName + requestUri );
					return;
				}
				
			}
			
			
			// Redirect uri to uriAlias, if present
			// NOTE: DO NOT redirect Facebook Scraping requests
			if( isWebApp && ( userAgent == null || userAgent.isEmpty() || ! userAgent.startsWith( "facebookexternalhit/1.1" ) ) ) {
				DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
				Page page = dataAccessor.getPage( requestUri );
				if( page != null && page.getUriAlias() != null && requestUri.equals( page.getUri() ) ) {
					response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
					response.setHeader( "Location", page.getUriAlias() );
					return;
				}
			}

			
			// Figuring out Browser capability
			
			boolean basicBrowser = false;

			if( isWebApp ) {

				if( userAgent == null || userAgent.trim().isEmpty() ) {
					basicBrowser = true;

				} else if( userAgent.contains( "UCBrowser" ) ) { // UCBrowser
					/*
					 * UCBrowser on Android 4.3
					 *   "Mozilla/5.0 (Linux; U; Android 4.3; en-US; GT-I9300 Build/JSS15J) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/10.0.1.512 U3/0.8.0 Mobile Safari/533.1"
					 */
					basicBrowser = true; // Polymer 1.0 not supported !

				} else if( userAgent.contains( "Opera Mobi" ) ) { // Opera Classic
					/*
					 * Opera Classic on Android 4.3
					 *   "Opera/9.80 (Android 4.3; Linux; Opera Mobi/ADR-1411061201) Presto/2.11.355 Version/12.10"
					 */
					basicBrowser = true; // Not sure whether Polymer 1.0 is supported or not

				} else if( userAgent.contains( "Opera Mini" ) ) { // Opera Mini
					/*
					 * Opera Mini on Android 4.3
					 *   "Opera/9.80 (Android; Opera Mini/7.6.40077/35.5706; U; en) Presto/2.8.119 Version/11.10"
					 */
					basicBrowser = true; // Polymer 1.0 is not supported !

				} else if( userAgent.contains( "Trident/7" ) && userAgent.contains( "rv:11" ) ) { // Microsoft Internet Explorer 11
					/*
					 * Microsoft Internet Explorer 11 on Microsoft Windows 8.1
					 *   "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; Touch; LCJB; rv:11.0) like Gecko"
					 */
					basicBrowser = true;

				} else if( userAgent.contains( "OPR" ) ) { // Opera
					/*
					 * Opera on Microsoft Windows 8.1
					 *   "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36 OPR/26.0.1656.24"
					 * Opera on Android 4.3
					 *   "Mozilla/5.0 (Linux; Android 4.3; GT-I9300 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.102 Mobile Safari/537.36 OPR/25.0.1619.84037"
					 */
					String userAgentSubStr = userAgent.substring( userAgent.indexOf( "OPR" ) + 4 );
					int version = Integer.parseInt( userAgentSubStr.substring( 0, userAgentSubStr.indexOf( "." ) ) );
					basicBrowser = version < 20;

				} else if( userAgent.contains( "Edge" ) ) {
					/* 
					 * Microsoft Edge browser on Windows 10
					 * Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393    
					 */
					basicBrowser = false;

				} else if( userAgent.contains( "Chrome" ) && ! userAgent.contains( "(Chrome)" ) ) { // Google Chrome
					/*
					 * Google Chrome on Microsoft Windows 8.1
					 *   "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36"
					 * Google Chrome on Android 4.3
					 *   "Mozilla/5.0 (Linux; Android 4.3; GT-I9300 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.59 Mobile Safari/537.36"
					 */
					String userAgentSubStr = userAgent.substring( userAgent.indexOf( "Chrome" ) + 7 );
					int version = Integer.parseInt( userAgentSubStr.substring( 0, userAgentSubStr.indexOf( "." ) ) );
					basicBrowser = version < 35;

				} else if( userAgent.contains( "Safari" ) ) { // Apple Safari
					/*
					 * Apple Safari on Microsoft Windows 8.1
					 *   Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2
					 */
					if( userAgent.contains( "Version" ) ) {
						String userAgentSubStr = userAgent.substring( userAgent.indexOf( "Version" ) + 8 );
						int version = Integer.parseInt( userAgentSubStr.substring( 0, userAgentSubStr.indexOf( "." ) ) );
						basicBrowser = version < 8;
					} else {
						String userAgentSubStr = userAgent.substring( userAgent.indexOf( "Safari" ) + 7 );
						int version = Integer.parseInt( userAgentSubStr.substring( 0, userAgentSubStr.indexOf( "." ) ) );
						basicBrowser = version < 538 || version > 620;
					}

				} else if( userAgent.contains( "Firefox" ) ) { // Mozilla Firefox
					/*
					 * Mozilla Firefox on Microsoft 8.1
					 *   "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0 AlexaToolbar/alxf-2.21"
					 * Mozilla Firefox on Android 4.3
					 *   "Mozilla/5.0 (Android; Mobile; rv:33.0) Gecko/33.0 Firefox/33.0"
					 * Mozilla Firefox on Linux 
					 *   "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20100101 Firefox/10.0 (Chrome)"
					 */
					String userAgentSubStr = userAgent.substring( userAgent.indexOf( "Firefox" ) + 8 );
					int version = Integer.parseInt( userAgentSubStr.substring( 0, userAgentSubStr.indexOf( "." ) ) );
					basicBrowser = version < 28;

				} else if( userAgent.contains( "Googlebot/2.1" ) ) { // Google Bot
					basicBrowser = false;

				} else if( userAgent.startsWith( "facebookexternalhit/1.1" ) ) { // Facebook Scraping requests
					basicBrowser = false;

				} else if( userAgent.startsWith( "WhatsApp" ) ) {
					basicBrowser = false;

				} else { // Unknown Browsers
					basicBrowser = true;

				}

			}
			

			// Redirecting requests coming from basic browsers to BasicMode
			if( basicBrowser && ! basicMode && website != null && website.getMobileHostName() != null ) {
				response.setStatus( HttpServletResponse.SC_MOVED_TEMPORARILY );
				String queryString = request.getQueryString();
				if( queryString == null || queryString.isEmpty() )
					response.setHeader( "Location", ( request.isSecure() ? "https://" : "http://" ) + website.getMobileHostName() + requestUri );
				else
					response.setHeader( "Location", ( request.isSecure() ? "https://" : "http://" ) + website.getMobileHostName() + requestUri + "?" + request.getQueryString() );
				return;
			}
			
			
			threadLocalBasicMode.set( basicMode );
			threadLocalWebsite.set( website );

		}
		
		chain.doFilter( req, resp );

		threadLocalBasicMode.remove();
		threadLocalWebsite.remove();
		
	}

	public static boolean isAndroidApp() {
		return isAndroidApp;
	}

	public static boolean isBasicMode() {
		return threadLocalBasicMode.get();
	}

	public static Website getWebsite() {
		return threadLocalWebsite.get();
	}

	@Deprecated
	public static Language getDisplayLanguage() {
		Website website = threadLocalWebsite.get();
		return website == null ? null : website.getDisplayLanguage();
	}

	@Deprecated
	public static Language getFilterLanguage() {
		Website website = threadLocalWebsite.get();
		return website == null ? null : website.getFilterLanguage();
	}

}