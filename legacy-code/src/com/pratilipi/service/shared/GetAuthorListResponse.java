package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.AuthorData;

public class GetAuthorListResponse implements IsSerializable {

	private List<AuthorData> authorDataList;
	
	private String cursor;

	
	@SuppressWarnings("unused")
	private GetAuthorListResponse() {}
	
	public GetAuthorListResponse( List<AuthorData> authorDataList, String cursor ) {
		this.authorDataList = authorDataList;
		this.cursor = cursor;
	}
	
	
	public List<AuthorData> getAuthorList() {
		return authorDataList;
	}
	
	public String getCursor() {
		return cursor;
	}
	
}
