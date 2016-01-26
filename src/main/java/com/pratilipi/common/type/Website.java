package com.pratilipi.common.type;

public enum Website {

	ALL_LANGUAGE(       "www.pratilipi.com",  "m.pratilipi.com", Language.ENGLISH,	 null ),
	HINDI		(     "hindi.pratilipi.com", "hi.pratilipi.com", Language.HINDI,	 Language.HINDI ),
	GUJARATI	(  "gujarati.pratilipi.com", "gu.pratilipi.com", Language.GUJARATI,	 Language.GUJARATI ),
	TAMIL		(     "tamil.pratilipi.com", "ta.pratilipi.com", Language.TAMIL,	 Language.TAMIL ),
	MARATHI		(   "marathi.pratilipi.com", "mr.pratilipi.com", Language.MARATHI,	 Language.MARATHI ),
	MALAYALAM	( "malayalam.pratilipi.com", "ml.pratilipi.com", Language.MALAYALAM, Language.MALAYALAM ),
	BENGALI		(   "bengali.pratilipi.com", "bn.pratilipi.com", Language.BENGALI,	 Language.BENGALI ),
	GAMMA		(     "gamma.pratilipi.com", "ga.pratilipi.com", Language.TAMIL,	 Language.TAMIL ),
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
