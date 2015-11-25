package com.pratilipi.service;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.impl.author.AuthorBackupApi;
import com.pratilipi.api.impl.author.AuthorProcessApi;
import com.pratilipi.api.impl.init.InitApi;
import com.pratilipi.api.impl.pratilipi.PratilipiBackupApi;
import com.pratilipi.api.impl.pratilipi.PratilipiIdfApi;
import com.pratilipi.api.impl.pratilipi.PratilipiProcessApi;
import com.pratilipi.api.impl.user.UserBackupApi;
import com.pratilipi.api.impl.user.UserEmailApi;
import com.pratilipi.api.impl.user.UserFacebookValidationApi;

@SuppressWarnings("serial")
public class WorkerService extends GenericService {
	
	static {
		
		ApiRegistry.register( InitApi.class );

		ApiRegistry.register( PratilipiProcessApi.class );
		ApiRegistry.register( PratilipiBackupApi.class );
		ApiRegistry.register( PratilipiIdfApi.class );

		ApiRegistry.register( AuthorProcessApi.class );
		ApiRegistry.register( AuthorBackupApi.class );

		ApiRegistry.register( UserEmailApi.class );
		ApiRegistry.register( UserFacebookValidationApi.class );
		ApiRegistry.register( UserBackupApi.class );
		
	}
	
}
