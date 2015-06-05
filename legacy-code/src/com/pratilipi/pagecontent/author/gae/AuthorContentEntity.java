package com.pratilipi.pagecontent.author.gae;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.author.AuthorContent;

@SuppressWarnings("serial")
public class AuthorContentEntity extends PageContentEntity
		implements AuthorContent {

	public AuthorContentEntity( Long authorId ) {
		super.setId( authorId );
	}
	
}
 