package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddLanguageResponse implements IsSerializable {

	private Long languageId;


	@SuppressWarnings("unused")
	private AddLanguageResponse() {}
	
	public AddLanguageResponse( Long languageId ) {
		this.languageId = languageId;
	}

	
	public Long getLanguageId() {
		return this.languageId;
	}
	
}
