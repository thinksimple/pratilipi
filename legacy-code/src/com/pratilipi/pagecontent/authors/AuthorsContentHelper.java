package com.pratilipi.pagecontent.authors;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.author.shared.AuthorContentData;
import com.pratilipi.pagecontent.authors.gae.AuthorsContentEntity;

public class AuthorsContentHelper extends PageContentHelper<
		AuthorsContent,
		AuthorContentData,
		AuthorsContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Author List";
	}

	@Override
	public Double getModuleVersion() {
		return 5.3;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static AuthorsContent newAuthorsContent() {
		return new AuthorsContentEntity();
	}
	
}
