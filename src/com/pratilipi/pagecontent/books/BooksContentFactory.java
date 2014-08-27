package com.pratilipi.pagecontent.books;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.books.gae.BooksContentEntity;

public class BooksContentFactory
		implements PageContentFactory<BooksContent, BooksContentProcessor> {
	
	public static BooksContent newBooksContent() {
		
		return new BooksContentEntity();
		
	}
	
}
