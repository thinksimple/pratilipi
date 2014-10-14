package com.claymus.pagecontent.blog;

import com.claymus.data.transfer.PageContent;

public interface BlogContent extends PageContent {
	
	String getCursor();
	
	void setCursor( String cursor );

	Integer getPostCount();
	
	void setPostCount( Integer postCount );
	
}
