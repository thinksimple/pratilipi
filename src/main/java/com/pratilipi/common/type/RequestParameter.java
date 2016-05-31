package com.pratilipi.common.type;

public enum RequestParameter {
	
	ACCESS_TOKEN ( "accessToken" ),
	LIST_PAGE_NUMBER( "page" ),
	READER_PAGE_NUMBER( "pageNo" ),
	READER_CONTENT_ID( "id" ),
	SEARCH_QUERY( "q" ),
	PRATILIPI_REVIEW( "review" ),
	PASSWORD_RESET_EMAIL_EMAIL( "email" ),
	PASSWORD_RESET_EMAIL_TOKEN( "token" ),
	PASSWORD_RESET_EMAIL_FLAG( "passwordReset" )
	;
	
	
	private String name;

	
	private RequestParameter( String name ) {
		this.name = name;
	}

	
	public String getName() {
		return name;
	}

}
