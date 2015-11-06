package com.pratilipi.service;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.author.AuthorImageApi;
import com.pratilipi.api.pratilipi.PratilipiContentApi;
import com.pratilipi.api.pratilipi.PratilipiCoverApi;
import com.pratilipi.api.pratilipi.PratilipiListApi;
import com.pratilipi.api.user.UserEmailApi;
import com.pratilipi.api.user.UserLoginApi;
import com.pratilipi.api.user.UserLoginFacebookApi;
import com.pratilipi.api.user.UserLogoutApi;
import com.pratilipi.api.user.UserPasswordUpdateApi;
import com.pratilipi.api.user.UserRegisterApi;
import com.pratilipi.api.user.UserVerificationApi;
import com.pratilipi.api.userpratilipi.UserPratilipiApi;
import com.pratilipi.api.userpratilipi.UserPratilipiReviewListApi;

@SuppressWarnings("serial")
public class PratilipiService extends GenericService {
	
	static {
		ApiRegistry.register( UserLoginApi.class );
		ApiRegistry.register( UserLoginFacebookApi.class );
		ApiRegistry.register( UserEmailApi.class );
		ApiRegistry.register( UserLogoutApi.class );
		ApiRegistry.register( UserRegisterApi.class );
		ApiRegistry.register( UserVerificationApi.class );
		ApiRegistry.register( UserPasswordUpdateApi.class );
		
		ApiRegistry.register( PratilipiListApi.class );
		ApiRegistry.register( PratilipiCoverApi.class );
		ApiRegistry.register( PratilipiContentApi.class );
		
		ApiRegistry.register( AuthorImageApi.class );
		
		ApiRegistry.register( UserPratilipiApi.class );
		ApiRegistry.register( UserPratilipiReviewListApi.class );
		
	}
	
}
