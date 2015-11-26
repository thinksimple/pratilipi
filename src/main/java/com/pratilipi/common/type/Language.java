package com.pratilipi.common.type;

public enum Language {

	// en.wikipedia.org/wiki/List_of_ISO_639-2_codes
	HINDI	 ( "hi", "हिंदी",		"Hindi",	     "hindi.pratilipi.com",     "m.hindi.pratilipi.com" ),
	GUJARATI ( "gu", "ગુજરાતી",	"Gujarati",	  "gujarati.pratilipi.com",  "m.gujarati.pratilipi.com" ),
	TAMIL	 ( "ta", "தமிழ்",	"Tamil",	     "tamil.pratilipi.com",     "m.tamil.pratilipi.com" ),
	MARATHI	 ( "mr", "मराठी",		"Marathi",	   "marathi.pratilipi.com",   "m.marathi.pratilipi.com" ),
	MALAYALAM( "ml", "മലയാളം",	"Malayalam", "malayalam.pratilipi.com", "m.malayalam.pratilipi.com" ),
	BENGALI	 ( "bn", "বাংলা",		"Bengali",     "bengali.pratilipi.com",   "m.bengali.pratilipi.com" ),
	ENGLISH  ( "en", "English",	"English",	       "www.pratilipi.com",           "m.pratilipi.com" ),
	;
	
	
	private final String code;
	private final String name;
	private final String nameEn;
	private final String hostName;
	private final String mobileHostName;
	
	
	private Language( String code, String name, String nameEn, String hostName, String mobileHostName ) {
		this.code = code;
		this.name = name;
		this.nameEn = nameEn;
		this.hostName = hostName;
		this.mobileHostName = mobileHostName;
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
	
	public String getHostName() {
		return hostName;
	}

	public String getMobileHostName() {
		return mobileHostName;
	}

}