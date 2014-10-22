package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetReaderContentRequest implements IsSerializable {

	private Long pratilipiId;
	
	private int pageNumber;

	
	@SuppressWarnings("unused")
	private GetReaderContentRequest() {}
	
	public GetReaderContentRequest( Long pratilipiId, int pageNumber ) {
		this.pratilipiId = pratilipiId;
		this.pageNumber = pageNumber;
	}

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public int getPageNumber() {
		return pageNumber;
	}
	
	
}
