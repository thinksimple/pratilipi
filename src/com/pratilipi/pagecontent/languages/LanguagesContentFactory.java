package com.pratilipi.pagecontent.languages;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.languages.gae.LanguagesContentEntity;

public class LanguagesContentFactory
		implements PageContentFactory<LanguagesContent, LanguagesContentProcessor> {
	
	public static LanguagesContent newLanguagesContent() {
		
		return new LanguagesContentEntity();
		
	}
	
}
