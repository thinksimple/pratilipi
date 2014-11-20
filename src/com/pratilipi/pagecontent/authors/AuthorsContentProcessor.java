package com.pratilipi.pagecontent.authors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Language;

public class AuthorsContentProcessor extends PageContentProcessor<AuthorsContent> {

	public static final String ACCESS_ID_AUTHOR_LIST = "author_list";
	public static final String ACCESS_ID_AUTHOR_READ_META_DATA = "author_read_meta_data";
	public static final String ACCESS_ID_AUTHOR_ADD = "author_add";
	
	
	@Override
	public String generateHtml( AuthorsContent authorsContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		boolean showMetaData =
				pratilipiHelper.hasUserAccess( ACCESS_ID_AUTHOR_READ_META_DATA, false );
		boolean showAddOption =
				pratilipiHelper.hasUserAccess( ACCESS_ID_AUTHOR_ADD, false );

		
		// Fetching Author list
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		List<Author> authorList = dataAccessor.getAuthorList( null, 250 ).getDataList();
		Map<String, String> languageIdNameMap = new HashMap<>();
		for( Author author : authorList ) {
			if( languageIdNameMap.get( author.getLanguageId() ) == null ) {
				Language language = dataAccessor.getLanguage( author.getLanguageId() );
				languageIdNameMap.put(
						language.getId().toString(),
						language.getName() + " (" + language.getNameEn() + ")" );
			}
		}
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "authorList", authorList );
		dataModel.put( "languageIdNameMap", languageIdNameMap );
		dataModel.put( "authorPageUrl", PratilipiPageType.AUTHOR.getUrlPrefix() );
		dataModel.put( "showMetaData", showMetaData );
		dataModel.put( "showAddOption", showAddOption );
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		

		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
	@Override
	protected String getTemplateName() {
		return "com/pratilipi/pagecontent/authors/AuthorsContent.ftl";
	}

}
