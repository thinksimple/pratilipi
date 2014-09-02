package com.pratilipi.pagecontent.authors;

import com.claymus.module.pagecontent.PageContentProcessor;

public class AuthorsContentProcessor extends PageContentProcessor<AuthorsContent> {

	@Override
	public String getHtml( AuthorsContent author ) {
		return "<script "
				+ "type='text/javascript' "
				+ "language='javascript' "
				+ "src='/pagecontent.authors/pagecontent.authors.nocache.js'"
				+ "></script>"
				+ "<div id='PageContent-Authors'></div>";
	}
	
}
