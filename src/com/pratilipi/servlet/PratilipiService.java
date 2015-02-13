package com.pratilipi.servlet;

import com.claymus.api.ApiRegistry;
import com.claymus.servlet.ClaymusService;
import com.pratilipi.api.InitApi;
import com.pratilipi.api.OAuthApi;
import com.pratilipi.api.PurchaseApi;
import com.pratilipi.pagecontent.pratilipi.api.PratilipiContentApi;
import com.pratilipi.pagecontent.pratilipi.api.PratilipiContentImageApi;
import com.pratilipi.pagecontent.pratilipi.api.PratilipiResourceApi;

@SuppressWarnings("serial")
public class PratilipiService extends ClaymusService {
	
	static {
		ApiRegistry.register( InitApi.class );
		ApiRegistry.register( OAuthApi.class );
		ApiRegistry.register( PurchaseApi.class );
		
		ApiRegistry.register( PratilipiContentApi.class );
		ApiRegistry.register( PratilipiContentImageApi.class );
		ApiRegistry.register( PratilipiResourceApi.class );
	}

}
