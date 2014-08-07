package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.LanguageData;

public class GetLanguageListResponse implements IsSerializable {

	private List<LanguageData> languageList;
	
	
	@SuppressWarnings("unused")
	private GetLanguageListResponse() {}
	
	public GetLanguageListResponse( List<LanguageData> languageList ) {
		this.languageList = languageList;
	}
	
	
	public List<LanguageData> getLanguageList() {
		return this.languageList;
	}
	
}
