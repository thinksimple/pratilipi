package com.pratilipi.module.pagecontent.homebook;

import com.claymus.data.transfer.PageContent;

public interface HomeBookContent extends PageContent {
	
	Long getCurrentUserId();
	
	void setCurrentUserId( Long userId );

	Long getBookId();
	
	void setBookId( Long bookId );
	
}
