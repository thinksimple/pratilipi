package com.pratilipi.module.pagecontent.manageauthors;

import com.claymus.module.pagecontent.PageContentFactory;

public class ManageAuthorsFactory
		implements PageContentFactory<ManageAuthors, ManageAuthorsProcessor> {
	
	public static ManageAuthors newAuthorDataInput() {
		
		return new ManageAuthors() {
			
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
