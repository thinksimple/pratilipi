package com.claymus.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.access.DataAccessorFactory;

@SuppressWarnings("serial")
public class FileUploadServlet extends HttpServlet {

	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		PrintWriter out = response.getWriter();
		if( DataAccessorFactory.getBlobAccessor().createBlob( request ) )
			out.print( "SUCCESS" );
		else
			out.print( "FAIL" );
		out.close();
	}
	
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		DataAccessorFactory.getBlobAccessor().serveBlob( request, response );
	}
	
}
