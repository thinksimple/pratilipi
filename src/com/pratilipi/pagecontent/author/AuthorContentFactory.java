package com.pratilipi.pagecontent.author;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.author.gae.AuthorContentEntity;

public class AuthorContentFactory
		implements PageContentFactory<AuthorContent, AuthorContentProcessor> {
	
	public static AuthorContent newAuthorContent( Long authorId ) {
		
		AuthorContentEntity authorContentEntity = new AuthorContentEntity();
		authorContentEntity.setAuthorId( authorId );
		
		return authorContentEntity;
		
	}

}
