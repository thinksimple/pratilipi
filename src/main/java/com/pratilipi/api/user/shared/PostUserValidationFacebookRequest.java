package com.pratilipi.api.user.shared;

import com.pratilipi.api.shared.GenericRequest;

public class PostUserValidationFacebookRequest extends GenericRequest {
	
	private String fbAccessToken;
	
	private String pratilipiAccessToken;
	
	
	public String getFbAccessToken() {
		return fbAccessToken;
	}
	
	public String getPratilipiAccessToken() {
		return pratilipiAccessToken;
	}
	
}
