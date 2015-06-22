package com.pratilipi.common.type;

public enum RequestParameter {
	
	ACCESS_TOKEN ( "accessToken" ),
	;
	
	
	private String name;

	
	private RequestParameter( String name ) {
		this.name = name;
	}

	
	public String getName() {
		return name;
	}

}
