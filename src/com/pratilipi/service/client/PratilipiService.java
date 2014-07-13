package com.pratilipi.service.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../pratilipiservice")
public interface PratilipiService extends RemoteService {
	
	String greetServer( String name ) throws IllegalArgumentException;
	
}
