package com.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetOAuthRequest extends GenericRequest {

	@Validate( regEx = REGEX_EMAIL )
	private String userId;
	private String userSecret;
	
	private Long publisherId;
	private String publisherSecret;
	

	public String getUserId() {
		return userId;
	}

	public String getUserSecret() {
		return userSecret;
	}
	
	public Long getPublisherId() {
		return publisherId;
	}

	public String getPublisherSecret() {
		return publisherSecret;
	}
	
}
