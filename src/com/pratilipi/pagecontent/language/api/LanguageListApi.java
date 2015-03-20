package com.pratilipi.pagecontent.language.api;

import java.util.ArrayList;
import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.pagecontent.language.LanguageContentHelper;
import com.pratilipi.pagecontent.language.api.shared.GetLanguageListRequest;
import com.pratilipi.pagecontent.language.api.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.data.LanguageData;


@SuppressWarnings("serial")
@Bind( uri = "/language/list" )
public class LanguageListApi extends GenericApi {
	
	@Get
	public GetLanguageListResponse getLanguage( GetLanguageListRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		List<Long> languageIdList = request.getLanguageIdList();
		
		List<LanguageData> languageDataList = new ArrayList<>();
		
		if( languageIdList == null || languageIdList.size() == 0 ) {
			List<Language> languageList = dataAccessor.getLanguageList();
			for( Language language : languageList ){
				if( language.getHidden() )
					languageList.remove( language );
			}
			
			languageDataList = LanguageContentHelper.createLanguageDataList( languageList );
		} else {
			List<Language> languageList = dataAccessor.getLanguageList( languageIdList );
			for( Language language : languageList ){
				if( language.getHidden() )
					languageList.remove( language );
			}
			
			languageDataList = LanguageContentHelper.createLanguageDataList( languageList );
		}
		
		return new GetLanguageListResponse( languageDataList );
	}
	
	
}
