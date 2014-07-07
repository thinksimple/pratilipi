package com.pratilipi.common;

public enum Language {

	IN_HINDI ( "Hindi" ),
	IN_ENGLISH ( "English (IN)" ),
	US_ENGLISH ( "English (US)" ),
	UK_ENGLISH ( "English (US)" ),
	;
	
	
	private String name;
	
	private Language( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
