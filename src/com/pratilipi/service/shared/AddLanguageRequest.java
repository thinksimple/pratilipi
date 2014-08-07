package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.LanguageData;

public class AddLanguageRequest implements IsSerializable {

	private LanguageData language;


	@SuppressWarnings("unused")
	private AddLanguageRequest() {}
	
	public AddLanguageRequest( LanguageData language ) {
		this.language = language;
	}
	
	
	public LanguageData getLanguage() {
		return this.language;
	}

}
