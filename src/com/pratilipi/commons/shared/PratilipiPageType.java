package com.pratilipi.commons.shared;

import com.claymus.commons.shared.PageType;

public enum PratilipiPageType implements PageType {
	
	PRATILIPI( "/pratilipi/" ),
	READ( "/read?id=" ),
	WRITE( "/write?id=" ),
	AUTHOR( "/author/" ),
	PUBLISHER( "/publisher/" ),
	;
	
	
	private String urlPrefix;
	
	
	private PratilipiPageType( String urlPrefix ) {
		this.urlPrefix = urlPrefix;
	}
	
	@Override
	public String getUrlPrefix() {
		return urlPrefix;
	}
	
}
