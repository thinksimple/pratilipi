package com.pratilipi.pagecontent.book;

import com.claymus.data.transfer.PageContent;

public interface BookContent extends PageContent {
	
	Long getBookId();
	
	void setBookId( Long bookId );
	
}
