package com.pratilipi.www.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.access.DataAccessorFactory;

@SuppressWarnings("serial")
public class ResourceServlet extends HttpServlet {
	
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		DataAccessorFactory
				.getBlobAccessor()
				.serveBlob( request.getRequestURI().substring( 10 ), response );
		
	}
	
}
