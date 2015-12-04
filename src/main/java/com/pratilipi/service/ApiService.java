package com.pratilipi.service;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.impl.author.AuthorImageApi;
import com.pratilipi.api.impl.pratilipi.PratilipiCoverApi;
import com.pratilipi.api.impl.user.UserLoginFacebookApi;

@SuppressWarnings("serial")
public class ApiService extends GenericService {
	
	static {
		ApiRegistry.register( UserLoginFacebookApi.class );	// *.pratilipi.com

		ApiRegistry.register( PratilipiCoverApi.class );	// *.pratilipi.com & AWS CloudFront
		
		ApiRegistry.register( AuthorImageApi.class );		// *.pratilipi.com & AWS CloudFront
	}
	
}
