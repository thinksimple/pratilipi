package com.pratilipi.pagecontent.languages;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.languages.gae.LanguagesContentEntity;
import com.pratilipi.pagecontent.languages.shared.LanguagesContentData;

public class LanguagesContentFactory extends PageContentHelper<
		LanguagesContent,
		LanguagesContentData,
		LanguagesContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Language List";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static LanguagesContent newLanguagesContent() {
		
		return new LanguagesContentEntity();
		
	}
	
}
