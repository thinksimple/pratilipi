package com.pratilipi.common.type;

public enum PageType {
	
	GENERIC( "/page/" ),

	HOME( "/" ),

	PRATILIPI( "/pratilipi/" ),
	READ( "/read?id=" ),
	WRITE( "/pratilipi-write?id=" ),

	AUTHOR( "/author/" ),
	AUTHOR_DASHBOARD( null ),
	
	EVENT_LIST( "/events/" ),
	EVENT( "/event/" ),
	
	BLOG( "/blog/" ),
	BLOG_POST( "/blogpost/" ),
	
	CATEGORY_LIST( null ),	
	STATIC( null ),

	;
	
	
	private String urlPrefix;
	
	
	private PageType( String urlPrefix ) {
		this.urlPrefix = urlPrefix;
	}
	
	public String getUrlPrefix() {
		return urlPrefix;
	}
	
}
