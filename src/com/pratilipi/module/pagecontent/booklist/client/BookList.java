package com.pratilipi.module.pagecontent.booklist.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;

public class BookList implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	public void onModuleLoad() {
		
		Window.alert( "Hello from BookList !" );
		
		pratilipiService.greetServer( null, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess( String result ) {
				Window.alert( result );
			}
			
			@Override
			public void onFailure( Throwable caught ) {
				Window.alert( "Call failed to server with error: " + caught.getMessage() );
			}
			
		} );
		
	}
	
}
