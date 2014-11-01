package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetSearchRequest implements IsSerializable {

	private String searchQuery;
	private String cursor;
	private int resultCount;

	
	@SuppressWarnings("unused")
	private GetSearchRequest() {}
	
	public GetSearchRequest( String searchQuery, String cursor, int resultCount ) {
		
		this.searchQuery = searchQuery;
		this.cursor = cursor;
		this.resultCount = resultCount;
	}
	
	public String getsearchQuery() {
		return searchQuery;
	}
	
	public String getCursor(){
		return this.cursor;
	}
	
	public int getResultCount(){
		return this.resultCount;
	}
	
}
