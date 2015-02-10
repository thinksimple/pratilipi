package com.pratilipi.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.servlet.ResourceServlet;

@SuppressWarnings("serial")
public class ResourceImageServlet extends ResourceServlet {

	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		super.doPost( request, response );
	}
	
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
	}
}
