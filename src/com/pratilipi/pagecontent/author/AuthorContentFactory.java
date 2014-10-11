package com.pratilipi.pagecontent.author;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.author.gae.AuthorContentEntity;
import com.pratilipi.pagecontent.author.shared.AuthorContentData;

public class AuthorContentFactory extends PageContentHelper<
		AuthorContent,
		AuthorContentData,
		AuthorContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Author Content";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static AuthorContent newAuthorContent( Long authorId ) {
		
		AuthorContentEntity authorContentEntity = new AuthorContentEntity();
		authorContentEntity.setAuthorId( authorId );
		
		return authorContentEntity;
		
	}

}
