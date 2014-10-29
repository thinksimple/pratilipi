package com.pratilipi.commons.shared;

import com.claymus.commons.shared.PageType;

public enum PratilipiPageType implements PageType {
	
	AUTHOR( "/author" ),
	PRATILIPI( "/pratilipi" );
	
	
	private String urlPrefix;
	
	
	private PratilipiPageType( String urlPrefix ) {
		this.urlPrefix = urlPrefix;
	}
	
	@Override
	public String getUrlPrefix() {
		return urlPrefix;
	}
	
}
