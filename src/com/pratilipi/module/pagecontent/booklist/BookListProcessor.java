package com.pratilipi.module.pagecontent.booklist;

import com.claymus.module.pagecontent.PageContentProcessor;

public class BookListProcessor extends PageContentProcessor<BookList> {

	@Override
	public String getHtml( BookList bookList ) {
		return "<script "
				+ "type='text/javascript' "
				+ "language='javascript' "
				+ "src='/pagecontent.booklist/pagecontent.booklist.nocache.js'"
				+ "></script>"
				+ "<div id='PageContent-BookList' class='container'></div>";
	}
	
}
