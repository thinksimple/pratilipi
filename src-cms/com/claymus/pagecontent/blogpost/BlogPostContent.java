package com.claymus.pagecontent.blogpost;

import com.claymus.data.transfer.PageContent;

public interface BlogPostContent extends PageContent {
	
	String getTitle();
	
	void setTitle( String title );
	
	String getContent();
	
	void setContent( String content );
	
}
