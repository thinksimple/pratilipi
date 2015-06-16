package com.pratilipi.common.type;

public enum Language {

	ENGLISH	( "en", "English", "English" ),
	HINDI	( "hi", "हिंदी",      "Hindi" ),
	GUJARATI( "gu", "ગુજરાતી",   "Gujarati" ),
	TAMIL	( "ta", "தமிழ்",   "Tamil" ),
	;
	
	
	private final String code;
	private final String name;
	private final String nameEn;
	
	
	private Language( String code, String name, String nameEn ) {
		this.code = code;
		this.name = name;
		this.nameEn = nameEn;
	}
	
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}

	public String getNameEn() {
		return nameEn;
	}
	
}