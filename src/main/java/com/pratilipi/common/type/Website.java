package com.pratilipi.common.type;

public enum Website {

	ALL_LANGUAGE	(            "www.pratilipi.com",  "m.pratilipi.com", Language.ENGLISH,		null ),
	HINDI			(          "hindi.pratilipi.com", "hi.pratilipi.com", Language.HINDI,		Language.HINDI ),
	GUJARATI		(       "gujarati.pratilipi.com", "gu.pratilipi.com", Language.GUJARATI,	Language.GUJARATI ),
	TAMIL			(          "tamil.pratilipi.com", "ta.pratilipi.com", Language.TAMIL,		Language.TAMIL ),
	MARATHI			(        "marathi.pratilipi.com", "mr.pratilipi.com", Language.MARATHI,		Language.MARATHI ),
	MALAYALAM		(      "malayalam.pratilipi.com", "ml.pratilipi.com", Language.MALAYALAM,	Language.MALAYALAM ),
	BENGALI			(        "bengali.pratilipi.com", "bn.pratilipi.com", Language.BENGALI,		Language.BENGALI ),
	KANNADA			(        "kannada.pratilipi.com", "kn.pratilipi.com", Language.KANNADA,		Language.KANNADA ),
	TELUGU			(         "telugu.pratilipi.com", "te.pratilipi.com", Language.TELUGU,		Language.TELUGU ),
	
	GAMMA_ALL_LANGUAGE	(       "www.gamma.pratilipi.com",  "m.gamma.pratilipi.com", Language.ENGLISH,		null ),
	GAMMA_HINDI			(     "hindi.gamma.pratilipi.com", "hi.gamma.pratilipi.com", Language.HINDI,		Language.HINDI ),
	GAMMA_HINDI_HTTPS	(              "gamma-dot-prod-pratilipi.appspot.com", null, Language.HINDI,		Language.HINDI ),
	GAMMA_GUJARATI		(  "gujarati.gamma.pratilipi.com", "gu.gamma.pratilipi.com", Language.GUJARATI,		Language.GUJARATI ),
	GAMMA_TAMIL			(     "tamil.gamma.pratilipi.com", "ta.gamma.pratilipi.com", Language.TAMIL,		Language.TAMIL ),
	GAMMA_MARATHI		(   "marathi.gamma.pratilipi.com", "mr.gamma.pratilipi.com", Language.MARATHI,		Language.MARATHI ),
	GAMMA_MALAYALAM		( "malayalam.gamma.pratilipi.com", "ml.gamma.pratilipi.com", Language.MALAYALAM,	Language.MALAYALAM ),
	GAMMA_BENGALI		(   "bengali.gamma.pratilipi.com", "bn.gamma.pratilipi.com", Language.BENGALI,		Language.BENGALI ),
	GAMMA_KANNADA		(   "kannada.gamma.pratilipi.com", "kn.gamma.pratilipi.com", Language.KANNADA,		Language.KANNADA ),
	GAMMA_TELUGU		(    "telugu.gamma.pratilipi.com", "te.gamma.pratilipi.com", Language.TELUGU,		Language.TELUGU ),

	DEVO_ALL_LANGUAGE	(       "www.devo-pratilipi.appspot.com",  "m.devo-pratilipi.appspot.com", Language.ENGLISH,	null ),
	DEVO_HINDI			(     "hindi.devo-pratilipi.appspot.com", "hi.devo-pratilipi.appspot.com", Language.HINDI,		Language.HINDI ),
	DEVO_GUJARATI		(  "gujarati.devo-pratilipi.appspot.com", "gu.devo-pratilipi.appspot.com", Language.GUJARATI,	Language.GUJARATI ),
	DEVO_TAMIL			(     "tamil.devo-pratilipi.appspot.com", "ta.devo-pratilipi.appspot.com", Language.TAMIL,		Language.TAMIL ),
	DEVO_MARATHI		(   "marathi.devo-pratilipi.appspot.com", "mr.devo-pratilipi.appspot.com", Language.MARATHI,	Language.MARATHI ),
	DEVO_MALAYALAM		( "malayalam.devo-pratilipi.appspot.com", "ml.devo-pratilipi.appspot.com", Language.MALAYALAM,	Language.MALAYALAM ),
	DEVO_BENGALI		(   "bengali.devo-pratilipi.appspot.com", "bn.devo-pratilipi.appspot.com", Language.BENGALI,	Language.BENGALI ),
	DEVO_RAGHU			(   "raghu.devo-pratilipi.appspot.com",   "ra.devo-pratilipi.appspot.com", Language.TAMIL,		Language.TAMIL ),	

	ALPHA	( "localhost", "localhost", Language.HINDI, Language.HINDI ),
	;
	
	
	private String hostName;
	private String mobileHostName;
	private Language displayLanguage;
	private Language filterLanguage;
	
	private Website( String hostName, String mobileHostName, Language displayLangauge, Language filterLanguage ) {
		this.hostName = hostName;
		this.mobileHostName = mobileHostName;
		this.displayLanguage = displayLangauge;
		this.filterLanguage = filterLanguage;
	}

	
	public String getHostName() {
		return hostName;
	}
	
	public String getMobileHostName() {
		return mobileHostName;
	}

	public Language getDisplayLanguage() {
		return displayLanguage;
	}

	public Language getFilterLanguage() {
		return filterLanguage;
	}

}
