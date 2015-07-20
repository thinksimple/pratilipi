package com.pratilipi.site;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.author.AuthorImageApi;
import com.pratilipi.api.pratilipi.PratilipiCoverApi;
import com.pratilipi.api.pratilipi.PratilipiListApi;
import com.pratilipi.api.user.UserLoginApi;
import com.pratilipi.api.user.UserLogoutApi;
import com.pratilipi.api.userpratilipi.PratilipiReviewListApi;
import com.pratilipi.api.userpratilipi.UserPratilipiApi;

@SuppressWarnings("serial")
public class PratilipiApi extends GenericService {
	
	static {
		ApiRegistry.register( PratilipiListApi.class );
		ApiRegistry.register( PratilipiCoverApi.class );
		
		ApiRegistry.register( AuthorImageApi.class );
		
		ApiRegistry.register( UserLoginApi.class );
		ApiRegistry.register( UserLogoutApi.class );

		ApiRegistry.register( UserPratilipiApi.class );
		ApiRegistry.register( PratilipiReviewListApi.class );
	}
	
}
