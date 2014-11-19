package com.pratilipi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class RobotsTxtServlet extends HttpServlet {
	
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		PrintWriter writer = response.getWriter();
		writer.println( "User-agent: *" );

		if( request.getServerName().equals( "www.pratilipi.com" ) ) {
			writer.println( "Allow: /" );
			writer.println( "Sitemap: http://www.pratilipi.com/service.sitemap" );
			
		} else {
			writer.println( "Disallow: /" );
		}
		
		writer.close();
		
	}
	
}
