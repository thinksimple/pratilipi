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
	private static final ThreadLocal<Boolean> threadLocalBasicMode = new ThreadLocal<Boolean>();
	private static final ThreadLocal<Boolean> threadLocalEmbedMode = new ThreadLocal<Boolean>();

	
	@Override
	public void init( FilterConfig arg0 ) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
			throws IOException, ServletException {
		
		HttpServletRequest request = ( HttpServletRequest ) req;
		String hostName = request.getServerName();
		String userAgent = request.getHeader( "user-agent" );
		
		String basicModeParam = request.getParameter( "basicMode" );
		String embedModeParam = request.getParameter( "embedMode" );
		
		
		Language lang = Language.ENGLISH;
		boolean basicMode = true;

		
		if( hostName.equals( "hindi.pratilipi.com") )
			lang = Language.HINDI;
		else if( hostName.equals( "gujarati.pratilipi.com") )
			lang = Language.GUJARATI;
		else if( hostName.equals( "tamil.pratilipi.com") )
			lang = Language.TAMIL;
		else if( hostName.equals( "marathi.pratilipi.com") )
			lang = Language.MARATHI;
		
		
		if( basicModeParam != null ) {
			basicMode = Boolean.parseBoolean( basicModeParam );

			
		} else if( userAgent == null || userAgent.isEmpty() ) {
			basicMode = true;
			
			
		} else if( userAgent.contains( "OPR" ) ) { // Opera
			/*
			 * Opera on Microsoft Windows 8.1
			 *   "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36 OPR/26.0.1656.24"
			 * Opera on Android 4.3
			 *   "Mozilla/5.0 (Linux; Android 4.3; GT-I9300 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.102 Mobile Safari/537.36 OPR/25.0.1619.84037"
			 */
			String userAgentSubStr = userAgent.substring( userAgent.indexOf( "OPR" ) + 4 );
			int version = Integer.parseInt( userAgentSubStr.substring( 0, userAgentSubStr.indexOf( "." ) ) );
			
			basicMode = version <= 22;
			

		} else if( userAgent.contains( "Opera" ) && userAgent.contains( "Opera Mobi" ) ) { // Opera Classic
			/*
			 * Opera Classic on Android 4.3
			 *   "Opera/9.80 (Android 4.3; Linux; Opera Mobi/ADR-1411061201) Presto/2.11.355 Version/12.10"
			 */
			basicMode = true; // Polymer 0.5.1 not supported !

			
		} else if( userAgent.contains( "Opera" ) && userAgent.contains( "Opera Mini" ) ) { // Opera Mini
			/*
			 * Opera Mini on Android 4.3
			 *   "Opera/9.80 (Android; Opera Mini/7.6.40077/35.5706; U; en) Presto/2.8.119 Version/11.10"
			 */
			basicMode = true; // Polymer 0.5.1 not supported !
			
			
		} else if( userAgent.contains( "Chrome" ) && !userAgent.contains( "(Chrome)" ) ) { // Google Chrome
			/*
			 * Google Chrome on Microsoft Windows 8.1
			 *   "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36"
			 * Google Chrome on Android 4.3
			 *   "Mozilla/5.0 (Linux; Android 4.3; GT-I9300 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.59 Mobile Safari/537.36"
			 */
			String userAgentSubStr = userAgent.substring( userAgent.indexOf( "Chrome" ) + 7 );
			int version = Integer.parseInt( userAgentSubStr.substring( 0, userAgentSubStr.indexOf( "." ) ) );

			basicMode = version <= 35;
		
			
		} else if( userAgent.contains( "UCBrowser" ) ) { // UCBrowser
			/*
			 * UCBrowser on Android 4.3
			 *   "Mozilla/5.0 (Linux; U; Android 4.3; en-US; GT-I9300 Build/JSS15J) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/10.0.1.512 U3/0.8.0 Mobile Safari/533.1"
			 */
			basicMode = true; // Polymer 0.5.1 not supported !
			
			
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
			
			basicMode = version <= 30;
			
			
		} else if( userAgent.contains( "Trident/7" ) && userAgent.contains( "rv:11" ) ) { // Microsoft Internet Explorer 11
			/*
			 * Microsoft Internet Explorer 11 on Microsoft Windows 8.1
			 *   "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; Touch; LCJB; rv:11.0) like Gecko"
			 */
			basicMode = false;

			
		} else if ( userAgent.contains( "Safari" ) ) { // Apple Safari
			/*
			 * Apple Safari on Microsoft Windows 8.1
			 *   "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2"
			 */
			basicMode = false;

		}
		

		
		threadLocalLanguage.set( lang );
		threadLocalBasicMode.set( basicMode );
		threadLocalEmbedMode.set( embedModeParam != null && Boolean.parseBoolean( embedModeParam ) );

		chain.doFilter( req, resp );

		threadLocalLanguage.remove();
		threadLocalBasicMode.remove();
		threadLocalEmbedMode.remove();
	}

	
	public static Language getUserLanguage() {
		return threadLocalLanguage.get();
	}
	
	public static boolean isModeBasic() {
		return threadLocalBasicMode.get();
	}
	
	public static boolean isModeEmbed() {
		return threadLocalEmbedMode.get();
	}

}