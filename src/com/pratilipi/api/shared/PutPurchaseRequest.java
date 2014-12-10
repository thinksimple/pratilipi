package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PutPurchaseRequest extends GenericRequest {

	private Long pratilipiId;
	private String userId;

	
	private PutPurchaseRequest() {
		super( null );
	}

	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public String getUserId() {
		return userId;
	}

}
