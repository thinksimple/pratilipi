package com.pratilipi.pagecontent.author.gae;

import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.author.AuthorContent;

public class AuthorContentEntity  extends PageContentEntity
						implements AuthorContent {

	@Persistent( column = "X_COL_0" )
	private Long authorId;
	
	@Override
	public Long getAuthorId() {		
		return this.authorId;
	}

	@Override
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

}
 