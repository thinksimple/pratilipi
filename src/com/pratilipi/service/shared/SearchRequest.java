package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SearchRequest implements IsSerializable {

	private String query;

	private String docType;
	
	private String cursor;
	
	private Integer resultCount;

	
	@SuppressWarnings("unused")
	private SearchRequest() {}
	
	public SearchRequest( String query, String docType, String cursor, Integer resultCount ) {
		this.query = query;
		this.docType = docType;
		this.cursor = cursor;
		this.resultCount = resultCount;
	}
	
	public String getQuery() {
		return query;
	}
	
	public String getDocType() {
		return docType;
	}
	
	public String getCursor() {
		return cursor;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}
	
}
