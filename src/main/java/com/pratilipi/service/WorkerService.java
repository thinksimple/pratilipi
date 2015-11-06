package com.pratilipi.service;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.author.AuthorBackupApi;
import com.pratilipi.api.author.AuthorProcessApi;
import com.pratilipi.api.pratilipi.PratilipiBackupApi;
import com.pratilipi.api.pratilipi.PratilipiIdfApi;
import com.pratilipi.api.pratilipi.PratilipiProcessApi;
import com.pratilipi.api.user.UserBackupApi;
import com.pratilipi.api.user.UserEmailApi;
import com.pratilipi.api.user.UserFacebookValidationApi;

@SuppressWarnings("serial")
public class WorkerService extends GenericService {
	
	static {
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
