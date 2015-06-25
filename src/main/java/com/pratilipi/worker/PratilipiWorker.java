package com.pratilipi.worker;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.author.AuthorProcessApi;
import com.pratilipi.api.pratilipi.PratilipiProcessApi;

@SuppressWarnings("serial")
public class PratilipiWorker extends GenericService {
	
	static {
		ApiRegistry.register( PratilipiProcessApi.class );
		ApiRegistry.register( AuthorProcessApi.class );
	}
	
}
