package com.pratilipi.servlet;

import com.claymus.api.ApiRegistry;
import com.claymus.servlet.ClaymusService;
import com.pratilipi.api.OAuthApi;
import com.pratilipi.api.PurchaseApi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentApi;


@SuppressWarnings("serial")
public class PratilipiService extends ClaymusService {
	
	static {
		ApiRegistry.register( OAuthApi.class );
		ApiRegistry.register( PurchaseApi.class );
		ApiRegistry.register( PratilipiContentApi.class );
	}

}
