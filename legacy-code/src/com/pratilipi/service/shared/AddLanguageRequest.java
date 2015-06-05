package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.LanguageData;

public class AddLanguageRequest implements IsSerializable {

	private LanguageData languageData;


	@SuppressWarnings("unused")
	private AddLanguageRequest() {}
	
	public AddLanguageRequest( LanguageData languageData ) {
		this.languageData = languageData;
	}
	
	
	public LanguageData getLanguage() {
		return languageData;
	}

}
