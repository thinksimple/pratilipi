package com.pratilipi.module.pagecontent.homebook;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.module.pagecontent.homebook.gae.HomeBookContentEntity;

public class HomeBookContentFactory
		implements PageContentFactory<HomeBookContent, HomeBookContentProcessor> {
	
	public static HomeBookContent newHomeBookContent() {
		
		return new HomeBookContentEntity();
	}

	public static HomeBookContent newHomeBookContent( Long bookId ) {
		
		HomeBookContent homeBookContent = new HomeBookContentEntity();
		homeBookContent.setBookId( bookId );
		return homeBookContent;
	}
	
}
