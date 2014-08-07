package com.pratilipi.module.pagecontent.managelanguages;

import com.claymus.module.pagecontent.PageContentFactory;

public class ManageLanguagesFactory
		implements PageContentFactory<ManageLanguages, ManageLanguagesProcessor> {
	
	public static ManageLanguages newManageLanguages() {
		
		return new ManageLanguages() {
			
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
