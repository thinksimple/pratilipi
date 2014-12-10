package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetOAuthRequest extends GenericRequest {

	private Long publisherId;
	private String publisherSecret;
	private String userEmail;
	

	private GetOAuthRequest() {
		super( null );
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public String getPublisherSecret() {
		return publisherSecret;
	}
	
	public String getUserEmail() {
		return userEmail;
	}

}
