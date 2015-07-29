package com.pratilipi.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
	
	private static final ThreadLocal<AccessToken> threadLocalAccessToken = new ThreadLocal<AccessToken>();

	private static boolean autoGenerate;
	
	
	@Override
	public void init( FilterConfig config ) throws ServletException {
		String autoGenerateParam = config.getInitParameter( "AutoGenerate" );
		autoGenerate = autoGenerateParam != null && autoGenerateParam.equalsIgnoreCase( "true" );
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

		if( autoGenerate ) {
	
			if( accessTokenId == null || accessTokenId.isEmpty() ) {
				accessTokenId = getCookieValue( RequestCookie.ACCESS_TOKEN.getName(), request );
			} else {
				Cookie cookie = new Cookie( RequestCookie.ACCESS_TOKEN.getName(), accessTokenId );
				cookie.setPath( "/" );
				response.addCookie( cookie );
			}
			
			accessToken = dataAccessor.getAccessToken( accessTokenId );
			if( accessToken == null || accessToken.isExpired() ) {
				accessToken = AccessTokenDataUtil.newUserAccessToken();
				accessTokenId = accessToken.getId();
				Cookie cookie = new Cookie( RequestCookie.ACCESS_TOKEN.getName(), accessToken.getId() );
				cookie.setPath( "/" );
				response.addCookie(cookie );
			}
			
		} else if( requestUri.equals( "/user/accesstoken" ) ) {
			
			chain.doFilter( request, response );
			return;
	
		} else {

			if( accessTokenId == null || accessTokenId.isEmpty() ) {
				final String requestPayload = IOUtils.toString( request.getInputStream(), "UTF-8" );
				if( requestPayload != null && ! requestPayload.isEmpty() ) {
					JsonObject requestPayloadJson = new Gson().fromJson( requestPayload, JsonElement.class ).getAsJsonObject();
					if( requestPayloadJson.get( RequestCookie.ACCESS_TOKEN.getName() ) != null )
						accessTokenId = requestPayloadJson.get( RequestCookie.ACCESS_TOKEN.getName() ).getAsString();
					request = new HttpServletRequestWrapper( request ) {
						
						public ServletInputStream getInputStream() throws IOException {
							return new ServletInputStream() {
								InputStream in = new ByteArrayInputStream( requestPayload.getBytes( StandardCharsets.UTF_8 ) );
								@Override
								public int read() throws IOException {
									return in.read();
								}
							};
						}

					};
				}
			}
			
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
	
	public final String getCookieValue( String cookieName, HttpServletRequest request ) {
		Cookie[] cookies = request.getCookies();
		if( cookies == null ) return null;
		for( Cookie cookie : cookies ) {
			if( cookie.getName().equals( cookieName ) )
				return cookie.getValue();
		}
		return null;
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
