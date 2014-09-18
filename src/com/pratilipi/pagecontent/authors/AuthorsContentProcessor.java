package com.pratilipi.pagecontent.authors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Language;

public class AuthorsContentProcessor extends PageContentProcessor<AuthorsContent> {

	public static final String ACCESS_ID_AUTHOR_LIST = "author_list";
	public static final String ACCESS_ID_AUTHOR_READ_META_DATA = "author_read_meta_data";
	public static final String ACCESS_ID_AUTHOR_ADD = "author_add";
	
	
	@Override
	public String getHtml( AuthorsContent authorsContent,
			HttpServletRequest request, HttpServletResponse response ) {
		
		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		boolean showMetaData =
				claymusHelper.hasUserAccess( ACCESS_ID_AUTHOR_READ_META_DATA, false );
		boolean showAddOption =
				claymusHelper.hasUserAccess( ACCESS_ID_AUTHOR_ADD, false );

		
		// Fetching Author list
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
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
		dataAccessor.destroy();
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "authorList", authorList );
		dataModel.put( "languageIdNameMap", languageIdNameMap );
		dataModel.put( "authorPageUrl", PratilipiHelper.URL_AUTHOR_PAGE );
		dataModel.put( "showMetaData", showMetaData );
		dataModel.put( "showAddOption", showAddOption );
		dataModel.put( "timeZone", claymusHelper.getCurrentUserTimeZone() );
		

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
	@Override
	protected String getTemplateName() {
		return "com/pratilipi/pagecontent/authors/AuthorsContent.ftl";
	}

}
