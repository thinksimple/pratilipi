package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class GetOAuthResponse extends GenericResponse { 
	
	private String accessToken;
	private Long expiry;

	
	@SuppressWarnings("unused")
	private GetOAuthResponse() {}
	
	public GetOAuthResponse( String accessToken, Long expiry ) {
		this.accessToken = accessToken;
		this.expiry = expiry;
	}
	
	
	public String getAccessToken() {
		return accessToken;
	}

	public Long getExpiry() {
		return expiry;
	}
	
}
