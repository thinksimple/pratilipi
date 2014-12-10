package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetPurchaseRequest extends GenericRequest {

	private Long bookId;
	private String userEmail;
	//AccessToken is present in GenericRequest.

	private GetPurchaseRequest() {
		super( null );
	}

	public Long getBookId() {
		return bookId;
	}

	public String getUserEmail() {
		return userEmail;
	}

}
