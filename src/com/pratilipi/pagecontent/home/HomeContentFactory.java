package com.pratilipi.pagecontent.home;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.home.gae.HomeContentEntity;

public class HomeContentFactory
		implements PageContentFactory<HomeContent, HomeContentProcessor> {
	
	public static HomeContent newHomeContent() {
		return new HomeContentEntity();
	}
	
}
