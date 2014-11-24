package com.pratilipi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class PratilipiService extends HttpServlet {

	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
	
		PrintWriter writer = response.getWriter();
		writer.println( 
				"{" +
					"\"id\":" + request.getParameter( "pratilipiId" ) + "," +
					"\"pageNo\":" + request.getParameter( "pageNo" ) + "," +
				    "\"content\":" + "\"<h2>Hello World ! " + request.getParameter( "pageNo" ) + "<h2>\"" +
				"}"
		    );
		writer.close();
	}
	
}
