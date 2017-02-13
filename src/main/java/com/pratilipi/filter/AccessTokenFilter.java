package com.pratilipi.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pratilipi.api.impl.user.UserAccessTokenApi;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.RequestCookie;
import com.pratilipi.common.type.RequestHeader;
import com.pratilipi.common.type.RequestParameter;
import com.pratilipi.common.type.Website;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.util.AccessTokenDataUtil;

public class AccessTokenFilter implements Filter {
	
	private static final ThreadLocal<AccessToken> threadLocalAccessToken = new ThreadLocal<AccessToken>();

	private static boolean autoGenerate;
	private static boolean isWorker = false;
	
	
	@Override
	public void init( FilterConfig config ) throws ServletException {
		String autoGenerateParam = config.getInitParameter( "AutoGenerate" );
		autoGenerate = autoGenerateParam != null && autoGenerateParam.equalsIgnoreCase( "true" );
		String moduleParam = config.getInitParameter( "Module" );
		isWorker = moduleParam != null && moduleParam.equalsIgnoreCase( "Worker" );
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
		String userAgent = request.getHeader( "user-agent" );
		
		String accessTokenId = request.getParameter( RequestParameter.ACCESS_TOKEN.getName() );
		accessTokenId = accessTokenId == null ? null : accessTokenId.trim();
		AccessToken accessToken;

		if( requestUri.equals( "/remote_api" ) || ( userAgent != null && userAgent.equals( "Amazon CloudFront" ) ) ) {
			
			accessToken = null;
			
		} else if( autoGenerate ) { // Used by gamma, default & api modules.
		
			String accessTokenCookie = getCookieValue( RequestCookie.ACCESS_TOKEN.getName(), request );
			
			if( ( accessTokenId == null || accessTokenId.isEmpty() ) && ( accessTokenCookie == null || accessTokenCookie.isEmpty() ) ) {
				accessToken = AccessTokenDataUtil.newUserAccessToken( request );
			} else {
				accessToken = accessTokenId != null && ! accessTokenId.isEmpty()
						? dataAccessor.getAccessToken( accessTokenId )
						: dataAccessor.getAccessToken( accessTokenCookie );
				if( accessToken == null || accessToken.isExpired() ) {
					accessToken = AccessTokenDataUtil.newUserAccessToken( request );
				} else if( accessToken.getExpiry().getTime() < new Date().getTime() + AccessTokenDataUtil.MIN_EXPIRY_MILLIS ) {
					accessToken.setExpiry( new Date( new Date().getTime() + AccessTokenDataUtil.MAX_EXPIRY_MILLIS ) );
					accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
				}
			}

			if( ! accessToken.getId().equals( accessTokenCookie ) ) {
				accessTokenId = accessToken.getId();
				setCookieValue( RequestCookie.ACCESS_TOKEN.getName(), accessTokenId, response );
			}
			
		} else if( isWorker ) { // Used by worker module.

			accessTokenId = dataAccessor.getAppProperty( AppProperty.WORKER_ACCESS_TOKEN_ID ).getValue();
			accessToken = dataAccessor.getAccessToken( accessTokenId );
			
		} else if( requestUri.equals( "/user/accesstoken" ) ) { // Used by gamma-android & android module.
			
			accessToken = AccessTokenDataUtil.newUserAccessToken( request );
			dispatchResponse( response, new UserAccessTokenApi.Response( accessToken.getId(), accessToken.getExpiry() ) );
			return;
	
		} else { // Used by gamma-android & android module.

			// TODO: Consider only header. Ignore accessToken from request param.
			if( accessTokenId == null || accessTokenId.isEmpty() ) {
				accessTokenId = request.getHeader( RequestHeader.ACCESS_TOKEN.getName() );
				accessTokenId = accessTokenId == null ? null : accessTokenId.trim();
			}
			
			if( accessTokenId == null || accessTokenId.isEmpty() ) {
				dispatchResponse( response, new InvalidArgumentException( "Access Token is missing." ) );
				return;

			} else if( ( accessToken = dataAccessor.getAccessToken( accessTokenId ) ) == null ) {
				dispatchResponse( response, new InvalidArgumentException( "Access Token is invalid." ) );
				return;
			
			} else if( accessToken.isExpired() ) {
				dispatchResponse( response, new InsufficientAccessException( "Access Token is expired." ) );
				return;

			} else if( accessToken.getExpiry().getTime() < new Date().getTime() + AccessTokenDataUtil.MIN_EXPIRY_MILLIS ) {
				
				accessToken.setExpiry( new Date( new Date().getTime() + AccessTokenDataUtil.MAX_EXPIRY_MILLIS ) );
				accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
				
			}

		}
		
		
		threadLocalAccessToken.set( accessToken );
		
		chain.doFilter( request, response );
		
		threadLocalAccessToken.remove();

	}
	
	public static AccessToken getAccessToken() {
		return threadLocalAccessToken.get();
	}
	
	public static String getCookieValue( String cookieName, HttpServletRequest request ) {
		Cookie[] cookies = request.getCookies();
		if( cookies == null ) return null;
		for( Cookie cookie : cookies ) {
			if( cookie.getName().equals( cookieName ) )
				return cookie.getValue().trim();
		}
		return null;
	}

	private void setCookieValue( String cookieName, String cookieValue, HttpServletResponse response ) {
		Cookie cookie = new Cookie( cookieName, cookieValue );
		cookie.setDomain( SystemProperty.STAGE == SystemProperty.STAGE_ALPHA ? "localhost" : "pratilipi.com" );
		if( UxModeFilter.getWebsite() == Website.GAMMA_HINDI_HTTPS )
			cookie.setDomain( Website.GAMMA_HINDI_HTTPS.getHostName() );
		cookie.setPath( "/" );
		response.addCookie( cookie );
	}

	// Ref: GenericApi.dispatchApiResponse
	private void dispatchResponse( HttpServletResponse response, UserAccessTokenApi.Response apiResponse )
			throws IOException {
		response.setContentType( "text/html" );
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter writer = response.getWriter();
		writer.println( new Gson().toJson( apiResponse ) );
		writer.close();
	}
	
	// Ref: GenericApi.dispatchApiResponse
	private void dispatchResponse( HttpServletResponse response, Throwable ex )
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
