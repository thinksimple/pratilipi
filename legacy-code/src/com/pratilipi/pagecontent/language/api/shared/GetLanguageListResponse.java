package com.pratilipi.pagecontent.language.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.LanguageData;

@SuppressWarnings( "serial" )
public class GetLanguageListResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private List<LanguageData> languageList;
	

	@SuppressWarnings("unused")
	private GetLanguageListResponse(){};
	
	public GetLanguageListResponse( List<LanguageData> languageDataList ) {
		this.languageList = languageDataList;
	}

}
