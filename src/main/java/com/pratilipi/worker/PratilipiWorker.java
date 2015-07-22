package com.pratilipi.worker;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.author.AuthorProcessApi;
import com.pratilipi.api.init.InitApi;
import com.pratilipi.api.pratilipi.PratilipiBackupApi;
import com.pratilipi.api.pratilipi.PratilipiIdfApi;
import com.pratilipi.api.pratilipi.PratilipiProcessApi;

@SuppressWarnings("serial")
public class PratilipiWorker extends GenericService {
	
	static {
		ApiRegistry.register( InitApi.class );
		
		ApiRegistry.register( PratilipiProcessApi.class );
		ApiRegistry.register( PratilipiBackupApi.class );
		ApiRegistry.register( PratilipiIdfApi.class );

		ApiRegistry.register( AuthorProcessApi.class );
	}
	
}
