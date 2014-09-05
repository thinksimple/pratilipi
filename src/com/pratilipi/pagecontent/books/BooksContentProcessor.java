package com.pratilipi.pagecontent.books;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.module.pagecontent.PageContentProcessor;

public class BooksContentProcessor extends PageContentProcessor<BooksContent> {

	public static String ACCESS_ID_BOOK_LIST = "book_list";
	public static String ACCESS_ID_BOOK_READ_META_DATA = "book_read_meta_data";
	public static String ACCESS_ID_BOOK_ADD = "book_add";
	public static String ACCESS_ID_BOOK_UPDATE = "book_update";
	
	
	@Override
	public String getHtml( BooksContent booksContent,
			HttpServletRequest request, HttpServletResponse response ) {
		
		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		@SuppressWarnings("unused")
		boolean showMetaData =
				claymusHelper.hasUserAccess( ACCESS_ID_BOOK_READ_META_DATA, false );
		boolean showAddOption =
				claymusHelper.hasUserAccess( ACCESS_ID_BOOK_ADD, false );

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "showAddOption", showAddOption );
		

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
	protected String getTemplateName() {
		return "com/pratilipi/pagecontent/books/BooksContent.ftl";
	}
	
}
