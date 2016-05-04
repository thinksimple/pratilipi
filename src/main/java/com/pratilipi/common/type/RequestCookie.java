package com.pratilipi.common.type;

public enum RequestCookie {
	
	ACCESS_TOKEN ( "access_token" ),
	FONT_SIZE ( "fontSize" ),
	PAGE_NO ( "pageNo" )
	;
	
	
	private String name;

	
	private RequestCookie( String name ) {
		this.name = name;
	}

	
	public String getName() {
		return name;
	}

}
