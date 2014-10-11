package com.pratilipi.pagecontent.languages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Language;

public class LanguagesContentProcessor
		extends PageContentProcessor<LanguagesContent> {

	public static final String ACCESS_ID_LANGUAGE_LIST = "language_list";
	public static final String ACCESS_ID_LANGUAGE_READ_META_DATA = "language_read_meta_data";
	public static final String ACCESS_ID_LANGUAGE_ADD = "language_add";
	
	
	@Override
	protected String generateHtml( LanguagesContent languagesContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		boolean showMetaData =
				pratilipiHelper.hasUserAccess( ACCESS_ID_LANGUAGE_READ_META_DATA, false );
		boolean showAddOption =
				pratilipiHelper.hasUserAccess( ACCESS_ID_LANGUAGE_ADD, false );

		
		// Fetching Language list
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Language> languageList = dataAccessor.getLanguageList();
		dataAccessor.destroy();

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "languageList", languageList );
		dataModel.put( "languagePageUrl", PratilipiHelper.URL_LANGUAGE_PAGE );
		dataModel.put( "showMetaData", showMetaData );
		dataModel.put( "showAddOption", showAddOption );
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}

	@Override
	protected String getTemplateName() {
		return "com/pratilipi/pagecontent/languages/LanguagesContent.ftl";
	}
	
}
