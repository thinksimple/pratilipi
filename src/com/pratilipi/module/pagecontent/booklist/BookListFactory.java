package com.pratilipi.module.pagecontent.booklist;

import com.claymus.module.pagecontent.PageContentFactory;

public class BookListFactory
		implements PageContentFactory<BookList, BookListProcessor> {
	
	public static BookList newBookList() {
		
		return new BookList() {
			
			@Override
			public Long getId() {
				return null;
			}

			@Override
			public Long getPageId() {
				return null;
			}
			
			@Override
			public void setPageId( Long pageId ) { }
			
			@Override
			public String getPosition() {
				return null;
			}
			
			@Override
			public void setPosition( String position ) { }

		};
		
	}
	
}
