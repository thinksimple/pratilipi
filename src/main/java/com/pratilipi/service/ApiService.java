package com.pratilipi.service;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.author.AuthorImageApi;
import com.pratilipi.api.pratilipi.PratilipiCoverApi;

@SuppressWarnings("serial")
public class ApiService extends GenericService {
	
	static {
		ApiRegistry.register( PratilipiCoverApi.class );

		ApiRegistry.register( PratilipiCoverApi.class );
		
		ApiRegistry.register( AuthorImageApi.class );
	}
	
}
