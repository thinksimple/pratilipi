package com.pratilipi.pagecontent.authors;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.authors.gae.AuthorsContentEntity;

public class AuthorsContentFactory
		implements PageContentFactory<AuthorsContent, AuthorsContentProcessor> {
	
	public static AuthorsContent newAuthorsContent() {
		
		return new AuthorsContentEntity();
		
	}
	
}
