package com.pratilipi.common.type;

public enum Language {

	ENGLISH	( "en" ),
	HINDI	( "hi" ),
	GUJARATI( "gu" ),
	TAMIL	( "ta" ),
	;
	
	
	private final String code;
	
	
	private Language( String code ) {
		this.code = code;
	}
	
	
	public String getCode() {
		return code;
	}
	
}