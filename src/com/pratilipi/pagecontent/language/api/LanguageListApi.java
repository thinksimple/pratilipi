package com.pratilipi.pagecontent.language.api;

import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.pratilipi.data.transfer.shared.LanguageData;
import com.pratilipi.pagecontent.language.LanguageContentHelper;
import com.pratilipi.pagecontent.language.api.shared.GetLanguageListRequest;
import com.pratilipi.pagecontent.language.api.shared.GetLanguageListResponse;


@SuppressWarnings("serial")
@Bind( uri = "/language/list" )
public class LanguageListApi extends GenericApi {
	
	@Get
	public GetLanguageListResponse getLanguage( GetLanguageListRequest request ) {
		
		List<LanguageData> languageList = LanguageContentHelper.getLanguageList(
				request.includeHidden(),
				this.getThreadLocalRequest() );
		
		return new GetLanguageListResponse( languageList );
	}

}
