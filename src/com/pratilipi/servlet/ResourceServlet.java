package com.pratilipi.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.apphosting.api.ApiProxy;

@SuppressWarnings("serial")
public class ResourceServlet extends HttpServlet {
	
	@SuppressWarnings("unused")
	private static final String APP_ID_DEVO = "devo-pratilipi";
	private static final String APP_ID_PROD = "prod-pratilipi";
	
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		String requestUri = request.getRequestURI();
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		
		response.sendRedirect( ( request.isSecure() ? "https://" : "http://" ) 
				+ "storage.googleapis.com/"
				+ ( appId.equals( APP_ID_PROD )
						? "prod-pratilipi.appspot.com"
						: "devo-pratilipi.appspot.com" )
				+ requestUri.substring( 9 ) );
		
	}
	
}
