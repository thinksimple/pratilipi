package com.pratilipi.pagecontent.book;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.book.gae.BookContentEntity;

public class BookContentFactory
		implements PageContentFactory<BookContent, BookContentProcessor> {
	
	public static BookContent newHomeBookContent() {
		
		return new BookContentEntity();
	}

	public static BookContent newHomeBookContent( Long bookId ) {
		BookContent homeBookContent = new BookContentEntity();
		homeBookContent.setBookId( bookId );
		return homeBookContent;
	}
	
}
