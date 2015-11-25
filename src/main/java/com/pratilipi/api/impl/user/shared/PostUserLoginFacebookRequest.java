package com.pratilipi.api.impl.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserLoginFacebookRequest extends GenericRequest {
	
	@Validate( required = true )
	private String fbUserAccessToken;
	
	
	public String getFbUserAccessToken() {
		return this.fbUserAccessToken;
	}

}
