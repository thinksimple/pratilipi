package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.AuthorData;

public class SaveAuthorRequest implements IsSerializable {

	private AuthorData authorData;


	@SuppressWarnings("unused")
	private SaveAuthorRequest() {}
	
	public SaveAuthorRequest( AuthorData authorData ) {
		this.authorData = authorData;
	}
	
	
	public AuthorData getAuthor() {
		return this.authorData;
	}

}
