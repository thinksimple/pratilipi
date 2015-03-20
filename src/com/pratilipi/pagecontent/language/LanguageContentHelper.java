package com.pratilipi.pagecontent.language;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.service.shared.data.LanguageData;

public class LanguageContentHelper {

	public static LanguageData createLanguageData( Long languageId, HttpServletRequest request ){
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		Language language = dataAccessor.getLanguage( languageId );
		
		return createLanguageData( language );
	}
	
	public static LanguageData createLanguageData( Language language ){
		if( language == null )
			return null;
		
		LanguageData languageData = new LanguageData();
		languageData.setId( language.getId() );
		languageData.setName( language.getName() );
		languageData.setNameEn( language.getNameEn() );
		languageData.setCreationDate( language.getCreationDate() );
		return languageData;
	}
	
	public static List<LanguageData> createLanguageDataList( List<Language> languageList ){
		
		List<LanguageData> languageDataList = new ArrayList<>( languageList.size() );
		for( Language language : languageList )
			languageDataList.add( createLanguageData( language ) );
		
		return languageDataList;
	}
}
