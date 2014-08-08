package com.pratilipi.module.pagecontent.manageauthors;

import com.claymus.module.pagecontent.PageContentProcessor;

public class ManageAuthorsProcessor extends PageContentProcessor<ManageAuthors> {

	@Override
	public String getHtml( ManageAuthors author ) {
		return "<script "
				+ "type='text/javascript' "
				+ "language='javascript' "
				+ "src='/pagecontent.manageauthors/pagecontent.manageauthors.nocache.js'"
				+ "></script>"
				+ "<div id='PageContent-ManageAuthors'></div>";
	}
	
}
