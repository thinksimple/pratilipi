package com.pratilipi.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

public class RemoteAPIFilter implements Filter {
	
	@Override
	public void init( FilterConfig config ) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter(
			ServletRequest req, ServletResponse resp, FilterChain chain )
					throws IOException, ServletException {

		HttpServletRequest request = ( HttpServletRequest ) req;
		String host = request.getServerName();

		if( host.equals( "alpha.pratilipi.com" ) ) {
			RemoteApiOptions options = new RemoteApiOptions()
					.server( "api.devo-pratilipi.appspot.com", 443 )
					.remoteApiPath( "/remote-api" )
					.credentials( "@pratilipi.com", "" );

			RemoteApiInstaller installer = new RemoteApiInstaller();
			try {
				installer.install( options );
				chain.doFilter( req, resp );
			} finally {
				try {
					installer.uninstall();
				} catch( Exception e ) {}
			}
			
		} else {
			chain.doFilter( req, resp );

		}
		
	}

}
