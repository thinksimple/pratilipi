package com.pratilipi.api.impl.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserFacebookValidationRequest extends GenericRequest {
	
	@Validate( required = true )
	private String fbAccessToken;
	
	@Validate( required = true )
	private String pratilipiAccessToken;
	
	
	public String getFbAccessToken() {
		return fbAccessToken;
	}
	
	public String getPratilipiAccessToken() {
		return pratilipiAccessToken;
	}
	
}
