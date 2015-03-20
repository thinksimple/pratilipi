package com.pratilipi.pagecontent.language.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class GetLanguageListRequest extends GenericRequest {
	private List<Long> languageIdList;
	
	public GetLanguageListRequest(){}
	
	public GetLanguageListRequest( List<Long> languageIdList ){
		this.languageIdList = languageIdList;
	}
	
	public List<Long> getLanguageIdList(){
		return this.languageIdList;
	}
	
}
