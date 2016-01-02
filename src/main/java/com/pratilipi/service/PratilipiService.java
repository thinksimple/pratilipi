package com.pratilipi.service;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.impl.author.AuthorApi;
import com.pratilipi.api.impl.author.AuthorImageApi;
import com.pratilipi.api.impl.init.InitApi;
import com.pratilipi.api.impl.pratilipi.PratilipiApi;
import com.pratilipi.api.impl.pratilipi.PratilipiContentApi;
import com.pratilipi.api.impl.pratilipi.PratilipiCoverApi;
import com.pratilipi.api.impl.pratilipi.PratilipiListApi;
import com.pratilipi.api.impl.user.UserEmailApi;
import com.pratilipi.api.impl.user.UserLoginApi;
import com.pratilipi.api.impl.user.UserLoginFacebookApi;
import com.pratilipi.api.impl.user.UserLogoutApi;
import com.pratilipi.api.impl.user.UserPasswordUpdateApi;
import com.pratilipi.api.impl.user.UserRegisterApi;
import com.pratilipi.api.impl.user.UserVerificationApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiReviewListApi;
import com.pratilipi.common.util.SystemProperty;

@SuppressWarnings("serial")
public class PratilipiService extends GenericService {
	
	static {
		ApiRegistry.register( InitApi.class );
		
		ApiRegistry.register( UserLoginApi.class );
		ApiRegistry.register( UserLoginFacebookApi.class );
		ApiRegistry.register( UserEmailApi.class );
		ApiRegistry.register( UserLogoutApi.class );
		ApiRegistry.register( UserRegisterApi.class );
		ApiRegistry.register( UserVerificationApi.class );
		ApiRegistry.register( UserPasswordUpdateApi.class );
		
		ApiRegistry.register( AuthorApi.class );
		ApiRegistry.register( PratilipiApi.class );
		ApiRegistry.register( PratilipiListApi.class );
		ApiRegistry.register( PratilipiContentApi.class );
		
		ApiRegistry.register( UserPratilipiApi.class );
		ApiRegistry.register( UserPratilipiReviewListApi.class );
		
		if( SystemProperty.CDN == null ) {
			ApiRegistry.register( PratilipiCoverApi.class );
			ApiRegistry.register( AuthorImageApi.class );
		}		
	}
	
}
