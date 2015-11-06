package com.pratilipi.api.user.shared;

import com.pratilipi.api.shared.GenericRequest;

public class PostUserValidationFacebookRequest extends GenericRequest {
	
	private Long userId;
	
	private String fbAccessToken;
	
	private String pratilipiAccessToken;
	
	
	public Long getUserId() { 
		return userId;
	}
	
	public String getFbAccessToken() {
		return fbAccessToken;
	}
	
	public String getPratilipiAccessToken() {
		return pratilipiAccessToken;
	}
	
}
