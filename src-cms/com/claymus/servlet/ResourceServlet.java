package com.claymus.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.access.DataAccessorFactory;

@SuppressWarnings("serial")
public class ResourceServlet extends HttpServlet {
	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		String fileName = request.getRequestURI().substring( 10 );
		if( ! DataAccessorFactory.getBlobAccessor().createBlob( request, fileName ) )
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
	}
	
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		DataAccessorFactory.getBlobAccessor()
				.serveBlob( request.getRequestURI().substring( 10 ), response );
	}
	
}
