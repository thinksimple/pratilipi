package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PutUserFacebookLoginRequest extends GenericRequest {
	
	@Validate( required = true )
	private String fbUserId; 
	
	@Validate( required = true )
	private String fbUserAccessToken;
	
	
	public String getFbUserId() {
		return this.fbUserId;
	}

	public String getFbUserAccessToken() {
		return this.fbUserAccessToken;
	}

}
