package com.pratilipi.common.type;

public enum PageType {
	
	GENERIC( "/page/" ),

	PRATILIPI( "/pratilipi/" ),
	READ( "/read?id=" ),
	WRITE( "/write?id=" ),

	AUTHOR( "/author/" ),
	AUTHOR_DASHBOARD( null ),
	
	@Deprecated
	PUBLISHER( "/publisher/" ),
	
	EVENT( "/event/" ),
	
	BLOG( "/blog/" ),
	BLOG_POST( "/blogpost/" ),
	;
	
	
	private String urlPrefix;
	
	
	private PageType( String urlPrefix ) {
		this.urlPrefix = urlPrefix;
	}
	
	public String getUrlPrefix() {
		return urlPrefix;
	}
	
}
