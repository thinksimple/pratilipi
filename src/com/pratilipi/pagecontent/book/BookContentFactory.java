package com.pratilipi.pagecontent.book;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.book.gae.BookContentEntity;

public class BookContentFactory
		implements PageContentFactory<BookContent, BookContentProcessor> {
	
	public static BookContent newBookContent() {
		
		return new BookContentEntity();
	}

	public static BookContent newBookContent( Long bookId ) {
		BookContent bookContent = new BookContentEntity();
		bookContent.setBookId( bookId );
		return bookContent;
	}
	
}
