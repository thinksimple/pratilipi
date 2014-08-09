package com.pratilipi.module.pagecontent.bookdatainput;

import com.claymus.module.pagecontent.PageContentProcessor;

public class BookDataInputProcessor extends PageContentProcessor<BookDataInput> {

	@Override
	public String getHtml( BookDataInput bookList ) {
		return "<script "
				+ "type='text/javascript' "
				+ "language='javascript' "
				+ "src='/pagecontent.bookdatainput/pagecontent.bookdatainput.nocache.js'"
				+ "></script>"
				+ "<div id='PageContent-BookDataInput'></div>";
	}
	
}
