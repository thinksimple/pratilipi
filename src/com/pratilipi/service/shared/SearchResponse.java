package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SearchResponse implements IsSerializable {

	private List<IsSerializable> dataList;
	
	private String cursor;
	
	
	@SuppressWarnings("unused")
	private SearchResponse() {}
	
	public SearchResponse( List<IsSerializable> dataList, String cursor ) {
		this.dataList = dataList;
		this.cursor = cursor;
	}
	
	
	public List<IsSerializable> getDataList() {
		return dataList;
	}

	public String getCursor() {
		return cursor;
	}
	
}
