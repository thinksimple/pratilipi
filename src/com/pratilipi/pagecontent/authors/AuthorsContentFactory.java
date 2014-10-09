package com.pratilipi.pagecontent.authors;

import com.claymus.commons.server.Access;
import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.authors.gae.AuthorsContentEntity;

public class AuthorsContentFactory
		implements PageContentFactory<AuthorsContent, AuthorsContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Author List Content";
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static AuthorsContent newAuthorsContent() {
		
		return new AuthorsContentEntity();
		
	}
	
}
