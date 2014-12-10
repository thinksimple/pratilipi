package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PutPurchaseRequest extends GenericRequest {

	private Long bookId;
	private String userEmail;

	
	private PutPurchaseRequest() {
		super( null );
	}

	
	public Long getBookId() {
		return bookId;
	}

	public String getUserEmail() {
		return userEmail;
	}

}
