package com.pratilipi.module.pagecontent.managelanguages;

import com.claymus.module.pagecontent.PageContentProcessor;

public class ManageLanguagesProcessor extends PageContentProcessor<ManageLanguages> {

	@Override
	public String getHtml( ManageLanguages manageLanguages ) {
		return "<script "
				+ "type='text/javascript' "
				+ "language='javascript' "
				+ "src='/pagecontent.managelanguages/pagecontent.managelanguages.nocache.js'"
				+ "></script>"
				+ "<div id='PageContent-ManageLanguages'></div>";
	}
	
}
