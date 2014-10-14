package com.claymus.pagecontent.blogpost.shared;

import com.claymus.service.shared.data.PageContentData;

public class BlogPostContentData extends PageContentData {

	private String title;
	
	private String content;

	private String pageUrl;

	private Boolean preview;

	
	public String getTitle() {
		return title;
	};
	
	public void setTitle( String title ) {
		this.title = title;
	};
	
	public String getContent() {
		return content;
	};
	
	public void setContent( String html ) {
		this.content = html;
	};
	
	public String getPageUrl() {
		return pageUrl;
	};
	
	public void setPageUrl( String pageUrl ) {
		this.pageUrl = pageUrl;
	};
	
	public Boolean preview() {
		return preview;
	};
	
	public void setPreview( Boolean preview ) {
		this.preview = preview;
	};
	
}
