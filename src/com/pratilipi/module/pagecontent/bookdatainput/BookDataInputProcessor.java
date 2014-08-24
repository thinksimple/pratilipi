package com.pratilipi.module.pagecontent.bookdatainput;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.module.pagecontent.PageContentProcessor;

public class BookDataInputProcessor extends PageContentProcessor<BookDataInput> {

	@Override
	public String getHtml( BookDataInput bookList, HttpServletRequest request, HttpServletResponse response ) {
		return "<script "
				+ "type='text/javascript' "
				+ "language='javascript' "
				+ "src='/pagecontent.bookdatainput/pagecontent.bookdatainput.nocache.js'"
				+ "></script>"
				+ "<div id='PageContent-BookDataInput'></div>";
	}
	
}
