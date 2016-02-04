package com.pratilipi.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.RequestCookie;
import com.pratilipi.common.type.RequestParameter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.util.AccessTokenDataUtil;

public class AccessTokenFilter implements Filter {
	
	private static final Logger logger = Logger.getLogger( AccessTokenFilter.class.getName() );

	private static final ThreadLocal<AccessToken> threadLocalAccessToken = new ThreadLocal<AccessToken>();

	private static boolean autoGenerate;
	private static Long runAsUserId;
	
	
	@Override
	public void init( FilterConfig config ) throws ServletException {
		String autoGenerateParam = config.getInitParameter( "AutoGenerate" );
		autoGenerate = autoGenerateParam != null && autoGenerateParam.equalsIgnoreCase( "true" );
		String runAsParam = config.getInitParameter( "RunAs" );
		runAsUserId = runAsParam == null ? null : Long.parseLong( runAsParam );
	}

	@Override
	public void destroy() { }

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
			throws IOException, ServletException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		HttpServletRequest request = ( HttpServletRequest ) req;
		HttpServletResponse response = ( HttpServletResponse ) resp;
		
		String requestUri = request.getRequestURI();
		
		String accessTokenId = request.getParameter( RequestParameter.ACCESS_TOKEN.getName() );
		AccessToken accessToken;

		if( autoGenerate ) { // Used by gamma, default & api modules.
		
			if( accessTokenId == null || accessTokenId.isEmpty() )
				accessTokenId = getCookieValue( RequestCookie.ACCESS_TOKEN.getName(), request );

			if( accessTokenId == null || accessTokenId.isEmpty() ) {
				accessToken = AccessTokenDataUtil.newUserAccessToken();
			} else {
				try {
					accessToken = AccessTokenDataUtil.renewUserAccessToken( accessTokenId );
				} catch( InvalidArgumentException e ) {
					logger.log( Level.SEVERE, "", e );
					accessToken = AccessTokenDataUtil.newUserAccessToken();
				}
			}

			if( ! accessToken.getId().equals( accessTokenId ) ) {
				accessTokenId = accessToken.getId();
				setCookieValue( RequestCookie.ACCESS_TOKEN.getName(), accessTokenId, response );
			}
			
		} else if( runAsUserId != null ) { // Used by worker module.

			accessToken = dataAccessor.getAccessTokenList( runAsUserId, new Date(), null, 1 ).getDataList().get( 0 );
			try {
				accessToken = AccessTokenDataUtil.renewUserAccessToken( accessToken.getId() );
			} catch( InvalidArgumentException e ) {
				// Do Nothing.
			}
			
		} else if( requestUri.equals( "/user/accesstoken" ) ) { // Used by android module.
			
			chain.doFilter( request, response );
			return;
	
		} else { // Used by android module.

			if( accessTokenId == null ) {
				dispatchResposne( response, new InvalidArgumentException( "Access Token is missing." ) );
				return;

			} else if( ( accessToken = dataAccessor.getAccessToken( accessTokenId ) ) == null ) {
				dispatchResposne( response, new InvalidArgumentException( "Access Token is invalid." ) );
				return;
			
			} else if( accessToken.isExpired() ) {
				dispatchResposne( response, new InsufficientAccessException( "Access Token is expired." ) );
				return;

			}

		}
		
		
		threadLocalAccessToken.set( accessToken );
		
		chain.doFilter( request, response );
		
		threadLocalAccessToken.remove();

	}
	
	public static AccessToken getAccessToken() {
		return threadLocalAccessToken.get();
	}
	
	private String getCookieValue( String cookieName, HttpServletRequest request ) {
		Cookie[] cookies = request.getCookies();
		if( cookies == null ) return null;
		for( Cookie cookie : cookies ) {
			if( cookie.getName().equals( cookieName ) )
				return cookie.getValue();
		}
		return null;
	}

	private void setCookieValue( String cookieName, String cookieValue, HttpServletResponse response ) {
		Cookie cookie = new Cookie( cookieName, cookieValue );
		cookie.setDomain( "pratilipi.com" );
		cookie.setPath( "/" );
		response.addCookie( cookie );
	}

	// Ref: GenericApi.dispatchApiResponse
	private void dispatchResposne( HttpServletResponse response, Throwable ex )
			throws IOException {
		
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter writer = response.getWriter();
		if( ex instanceof InvalidArgumentException )
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST );
		else if( ex instanceof InsufficientAccessException )
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		else if( ex instanceof UnexpectedServerException )
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		else
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		writer.println( ex.getMessage() );
		writer.close();
	}

}
