package com.pratilipi.pagecontent.authors;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.author.shared.AuthorContentData;
import com.pratilipi.pagecontent.authors.gae.AuthorsContentEntity;

public class AuthorsContentFactory extends PageContentFactory<
		AuthorsContent,
		AuthorContentData,
		AuthorsContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Author List Content";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static AuthorsContent newAuthorsContent() {
		
		return new AuthorsContentEntity();
		
	}
	
}
