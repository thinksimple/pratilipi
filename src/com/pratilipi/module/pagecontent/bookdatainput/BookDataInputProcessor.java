package com.pratilipi.module.pagecontent.bookdatainput;

import com.claymus.module.pagecontent.PageContentProcessor;

public class BookDataInputProcessor implements PageContentProcessor<BookDataInput> {

	@Override
	public String getHtml( BookDataInput bookList ) {
		return "<script "
				+ "type='text/javascript' "
				+ "language='javascript' "
				+ "src='/pagecontent_bookdatainput/pagecontent_bookdatainput.nocache.js'"
				+ "></script>"
				+ "<div id='Pratilipi-BookDataInput'></div>";
	}
	
}
