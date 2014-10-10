package com.pratilipi.pagecontent.languages;

import com.claymus.commons.server.Access;
import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.languages.gae.LanguagesContentEntity;

public class LanguagesContentFactory
		implements PageContentFactory<LanguagesContent, LanguagesContentProcessor> {
	
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
