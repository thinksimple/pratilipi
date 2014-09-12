package com.pratilipi.pagecontent.author;

import com.claymus.data.transfer.PageContent;

public interface AuthorContent extends PageContent {
	Long getAuthorId();
	
	void setAuthorId( Long authorId );
}
