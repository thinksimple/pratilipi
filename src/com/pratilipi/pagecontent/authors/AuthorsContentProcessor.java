package com.pratilipi.pagecontent.authors;

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
import com.pratilipi.data.transfer.Author;

public class AuthorsContentProcessor extends PageContentProcessor<AuthorsContent> {

	public static String ACCESS_ID_AUTHOR_LIST = "author_list";
	public static String ACCESS_ID_AUTHOR_READ_META_DATA = "author_read_meta_data";
	public static String ACCESS_ID_AUTHOR_ADD = "author_add";
	
	
	@Override
	public String getHtml( AuthorsContent authorsContent,
			HttpServletRequest request, HttpServletResponse response ) {
		
		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		boolean showMetaData =
				claymusHelper.hasUserAccess( ACCESS_ID_AUTHOR_READ_META_DATA, false );
		boolean showAddOption =
				claymusHelper.hasUserAccess( ACCESS_ID_AUTHOR_ADD, false );

		
		// Fetching Language list
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<Author> authorList = dataAccessor.getAuthorList( null, 100 ).getDataList();
		dataAccessor.destroy();
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "authorList", authorList );
		dataModel.put( "authorPageUrl", PratilipiHelper.AUTHOR_PAGE_URL );
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
