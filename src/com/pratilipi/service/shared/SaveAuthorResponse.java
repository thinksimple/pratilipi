package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.AuthorData;

public class SaveAuthorResponse implements IsSerializable {

	private AuthorData authorData;

	
	@SuppressWarnings("unused")
	private SaveAuthorResponse() {}
	
	public SaveAuthorResponse( AuthorData authorData ) {
		this.authorData = authorData;
	}
	
	
	public AuthorData getAuthorData() {
		return authorData;
	}
	
}
