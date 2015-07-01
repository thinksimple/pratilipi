package com.pratilipi.site;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.pratilipi.PratilipiCoverApi;

@SuppressWarnings("serial")
public class PratilipiApi extends GenericService {
	
	static {
		ApiRegistry.register( PratilipiCoverApi.class );
	}
	
}
