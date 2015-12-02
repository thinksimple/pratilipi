package com.pratilipi.common.type;

public enum PageType {
	
	GENERIC( "/page/" ),

	PRATILIPI( "/pratilipi/" ),
	READ( "/read/" ),
	WRITE( "/write/" ),

	AUTHOR( "/author/" ),
	AUTHOR_DASHBOARD( null ),
	
	EVENT( "/event/" ),
	;
	
	
	private String urlPrefix;
	
	
	private PageType( String urlPrefix ) {
		this.urlPrefix = urlPrefix;
	}
	
	public String getUrlPrefix() {
		return urlPrefix;
	}
	
}
