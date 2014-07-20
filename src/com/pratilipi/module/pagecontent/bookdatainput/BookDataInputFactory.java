package com.pratilipi.module.pagecontent.bookdatainput;

import com.claymus.module.pagecontent.PageContentFactory;

public class BookDataInputFactory
		implements PageContentFactory<BookDataInput, BookDataInputProcessor> {
	
	public static BookDataInput newBookDataInput() {
		
		return new BookDataInput() {
			
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
