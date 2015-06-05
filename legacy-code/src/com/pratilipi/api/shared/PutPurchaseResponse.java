package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class PutPurchaseResponse extends GenericResponse { 
	
	private String transactionId;

	
	@SuppressWarnings("unused")
	private PutPurchaseResponse() {}
	
	public PutPurchaseResponse( String transactionId ) {
		this.transactionId = transactionId;
	}
	
	
	public String getTransactionId() {
		return transactionId;
	}
	
}
