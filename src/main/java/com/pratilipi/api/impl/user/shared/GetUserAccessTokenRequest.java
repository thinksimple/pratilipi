package com.pratilipi.api.impl.user.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetUserAccessTokenRequest extends GenericRequest {

	private String accessToken;

	
	public String getAccessToken() {
		return accessToken;
	}
	
}
