package com.pratilipi.api.impl.user.shared;

import java.util.Date;

import com.pratilipi.api.shared.GenericResponse;

@SuppressWarnings("unused")
public class GetUserAccessTokenResponse extends GenericResponse {
	
	private String accessToken;
	private Long expiryMills;

	
	public GetUserAccessTokenResponse( String accessToken, Date expiry ) {
		this.accessToken = accessToken;
		this.expiryMills = expiry.getTime();
	}
	
}
