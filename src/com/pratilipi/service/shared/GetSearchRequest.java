package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetSearchRequest implements IsSerializable {

	private String searchQuery;

	
	@SuppressWarnings("unused")
	private GetSearchRequest() {}
	
	public GetSearchRequest( String searchQuery ) {
		
		this.searchQuery = searchQuery;
	}
	
	public String getsearchQuery() {
		return searchQuery;
	}
	
}
