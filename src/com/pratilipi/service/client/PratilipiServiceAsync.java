package com.pratilipi.service.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PratilipiServiceAsync {
	
	void greetServer( String input, AsyncCallback<String> callback )
			throws IllegalArgumentException;
	
}
