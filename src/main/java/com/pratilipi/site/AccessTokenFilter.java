package com.pratilipi.site;

import java.io.IOException;
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

import com.pratilipi.common.type.AccessTokenType;
import com.pratilipi.common.type.RequestCookie;
import com.pratilipi.common.type.RequestParameter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AccessToken;

public class AccessTokenFilter implements Filter {
	
	private static final ThreadLocal<AccessToken> threadLocalAccessToken = new ThreadLocal<AccessToken>();

	
	@Override
	public void init( FilterConfig config ) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
			throws IOException, ServletException {

		HttpServletRequest request = ( HttpServletRequest ) req;
		HttpServletResponse response = ( HttpServletResponse ) resp;

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		String accessTokenId = request.getParameter( RequestParameter.ACCESS_TOKEN.getName() );
		if( accessTokenId == null || accessTokenId.isEmpty() ) {
			accessTokenId = getCookieValue( RequestCookie.ACCESS_TOKEN.getName(), request );
		} else {
			Cookie cookie = new Cookie( RequestCookie.ACCESS_TOKEN.getName(), accessTokenId );
			cookie.setPath( "/" );
			response.addCookie( cookie );
		}
		
		AccessToken accessToken = dataAccessor.getAccessToken( accessTokenId );
		if( accessToken == null || accessToken.isExpired() ) {
			accessToken = dataAccessor.newAccessToken();
			accessToken.setUserId( 0L );
			accessToken.setType( AccessTokenType.USER );
			accessToken.setExpiry( new Date( new Date().getTime() + 604800000 ) ); // 1Wk
			accessToken = dataAccessor.createAccessToken( accessToken );
			
			accessTokenId = accessToken.getId();
			Cookie cookie = new Cookie( RequestCookie.ACCESS_TOKEN.getName(), accessToken.getId() );
			cookie.setPath( "/" );
			response.addCookie(cookie );
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
	
}
