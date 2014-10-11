package com.claymus.pagecontent.blogpost.shared;

import com.claymus.service.shared.data.PageContentData;

public class BlogPostContentData extends PageContentData {

	private String title;
	
	private String content;

	
	public String getTitle() {
		return title;
	};
	
	public void setTitle( String title ) {
		this.title = title;
	};
	
	public String getContent() {
		return content;
	};
	
	public void setContent( String content ) {
		this.content = content;
	};
	
}
