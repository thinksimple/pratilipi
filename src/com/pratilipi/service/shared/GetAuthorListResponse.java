package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.AuthorData;

public class GetAuthorListResponse implements IsSerializable {

	private List<AuthorData> authorDataList;
	
	
	@SuppressWarnings("unused")
	private GetAuthorListResponse() {}
	
	public GetAuthorListResponse( List<AuthorData> authorDataList ) {
		this.authorDataList = authorDataList;
	}
	
	
	public List<AuthorData> getAuthorList() {
		return this.authorDataList;
	}
	
}
