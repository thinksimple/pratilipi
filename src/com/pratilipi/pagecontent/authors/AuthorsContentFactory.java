package com.pratilipi.pagecontent.authors;

import com.claymus.module.pagecontent.PageContentFactory;

public class AuthorsContentFactory
		implements PageContentFactory<AuthorsContent, AuthorsContentProcessor> {
	
	public static AuthorsContent newAuthorsContent() {
		
		return new AuthorsContent() {
			
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
