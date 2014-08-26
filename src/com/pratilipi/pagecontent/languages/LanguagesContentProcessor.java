package com.pratilipi.pagecontent.languages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Language;

public class LanguagesContentProcessor
		extends PageContentProcessor<LanguagesContent> {

	public static String ACCESS_ID_LANGUAGE_LIST = "language_list";
	public static String ACCESS_ID_LANGUAGE_READ_META_DATA = "language_read_meta_data";

	public static String ACCESS_ID_LANGUAGE_ADD = "language_add";
	
	
	@Override
	public String getHtml( LanguagesContent languagesContent,
			HttpServletRequest request, HttpServletResponse response ) {
		
		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Language> languageList = dataAccessor.getLanguageList();
		dataAccessor.destroy();
		
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "languageList", languageList );
		
		dataModel.put( "languagePageUrl", PratilipiHelper.URL_LANGUAGE_PAGE );
		
		dataModel.put( "showMetaData",
				claymusHelper.hasUserAccess( ACCESS_ID_LANGUAGE_READ_META_DATA, false ) );
		
		dataModel.put( "showAddOption",
				claymusHelper.hasUserAccess( ACCESS_ID_LANGUAGE_ADD, false ) );
		
		return super.processTemplate(
				dataModel,
				"com/pratilipi/pagecontent/languages/LanguagesContent.ftl" );
	}
	
}
