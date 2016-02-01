package com.pratilipi.common.type;

public enum RequestParameter {
	
	ACCESS_TOKEN ( "accessToken" ),
	PAGE_NUMBER( "page" ),
	SEARCH_QUERY( "q" ),
	PRATILIPI_REVIEW( "review" )
	;
	
	
	private String name;

	
	private RequestParameter( String name ) {
		this.name = name;
	}

	
	public String getName() {
		return name;
	}

}
