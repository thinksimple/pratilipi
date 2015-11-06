package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserValidationFacebookRequest extends GenericRequest {
	
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
