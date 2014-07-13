package com.pratilipi.service.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pratilipi.service.client.PratilipiService;

@SuppressWarnings("serial")
public class PratilipiServiceImpl
		extends RemoteServiceServlet
		implements PratilipiService {

	public String greetServer( String input ) throws IllegalArgumentException {

		return "Hello from server !";
		
	}

}
