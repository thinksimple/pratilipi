package com.pratilipi.pagecontent.home;

import com.claymus.commons.server.Access;
import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.home.gae.HomeContentEntity;

public class HomeContentFactory
		implements PageContentFactory<HomeContent, HomeContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Pratilipi Home";
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static HomeContent newHomeContent() {
		return new HomeContentEntity();
	}
	
}
