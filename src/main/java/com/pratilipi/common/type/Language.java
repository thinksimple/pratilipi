package com.pratilipi.common.type;

public enum Language {

	// en.wikipedia.org/wiki/List_of_ISO_639-2_codes
	ENGLISH	 ( "en", "English", "English" ),
	HINDI	 ( "hi", "हिंदी",      "Hindi" ),
	GUJARATI ( "gu", "ગુજરાતી",   "Gujarati" ),
	TAMIL	 ( "ta", "தமிழ்",   "Tamil" ),
	MARATHI	 ( "mr", "मराठी",     "Marathi" ),
	MALAYALAM( "ml", "മലയാളം", "Malayalam" ),
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