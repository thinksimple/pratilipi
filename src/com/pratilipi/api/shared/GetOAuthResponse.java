package com.pratilipi.api.shared;

import java.util.Date;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class GetOAuthResponse extends GenericResponse { 
	
	private String accessToken;
	private Date expiry;

	
	@SuppressWarnings("unused")
	private GetOAuthResponse() {}
	
	public GetOAuthResponse( String accessToken, Date expiry ) {
		this.accessToken = accessToken;
		this.expiry = expiry;
	}
	
	
	public String getAccessToken() {
		return accessToken;
	}

	public Date getExpiry() {
		return expiry;
	}
	
}
