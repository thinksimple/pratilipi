package com.pratilipi.module.pagecontent.booklist;

import com.claymus.module.pagecontent.PageContentProcessor;

public class BookListProcessor implements PageContentProcessor<BookList> {

	@Override
	public String getHtml( BookList bookList ) {
		return "<script "
				+ "type='text/javascript' "
				+ "language='javascript' "
				+ "src='/pagecontent_booklist/pagecontent_booklist.nocache.js'"
				+ "></script>"
				+ "<div id='Pratilipi-BookList'></div>";
	}
	
}
