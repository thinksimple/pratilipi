package com.pratilipi.pagecontent.home;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.home.gae.HomeContentEntity;
import com.pratilipi.pagecontent.home.shared.HomeContentData;

public class HomeContentFactory extends PageContentHelper<
		HomeContent,
		HomeContentData,
		HomeContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Pratilipi Home";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static HomeContent newHomeContent() {
		return new HomeContentEntity();
	}
	
}
