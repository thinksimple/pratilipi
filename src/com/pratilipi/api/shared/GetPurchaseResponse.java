package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class GetPurchaseResponse extends GenericResponse { 
	
	private String transactionId;

	
	@SuppressWarnings("unused")
	private GetPurchaseResponse() {}
	
	public GetPurchaseResponse( String transactionId ) {
		this.transactionId = transactionId;
	}
	
	
	public String getTransactionId() {
		return transactionId;
	}

	
}
