package com.pratilipi.pagecontent.language.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.LanguageData;

@SuppressWarnings( "serial" )
public class GetLanguageListResponse extends GenericResponse {
	private List<LanguageData> languageDataList;
	
	protected GetLanguageListResponse(){};
	
	public GetLanguageListResponse( List<LanguageData> languageDataList ){
		this.languageDataList = languageDataList;
	}
	
	public List<LanguageData> getLanguageDataList(){
		return this.languageDataList;
	}
}
