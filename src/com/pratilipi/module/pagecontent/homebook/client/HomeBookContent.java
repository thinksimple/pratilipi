package com.pratilipi.module.pagecontent.homebook.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;

public class HomeBookContent implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	public void onModuleLoad() {
		
		Window.alert( "Hello Book Page !" );
		
	}
}
