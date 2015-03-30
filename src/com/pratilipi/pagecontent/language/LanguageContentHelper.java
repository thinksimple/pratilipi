package com.pratilipi.pagecontent.language;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.shared.LanguageData;
import com.pratilipi.pagecontent.language.shared.LanguageContentData;

public class LanguageContentHelper extends PageContentHelper<
		LanguageContent,
		LanguageContentData,
		LanguageContentProcessor>{

	@Override
	public String getModuleName() {
		return "Language";
	}

	@Override
	public Double getModuleVersion() {
		return 5.3;
	}
	

	public static LanguageData createLanguageData( Language language ) {
		if( language == null )
			return null;
		
		LanguageData languageData = new LanguageData();
		languageData.setId( language.getId() );
		languageData.setName( language.getName() );
		languageData.setNameEn( language.getNameEn() );
		languageData.setCreationDate( language.getCreationDate() );
		return languageData;
	}
	

	public static List<LanguageData> getLanguageList( boolean includeHidden, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		List<Language> languageList = dataAccessor.getLanguageList();

		List<LanguageData> languageDataList = new ArrayList<>( languageList.size() );
		for( Language language : languageList )
			if( !language.getHidden() || includeHidden )
				languageDataList.add( createLanguageData( language ) );

		return languageDataList;
	}
	
}