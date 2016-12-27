package com.pratilipi.common.type;

public enum I18nGroup {

	NOTIFICATION (
			"notification_and",
			// PRATILIPI_ADD
			"notification_has_published",	// Chetan Bhagat has published a new content : 2 States
			// AUTHOR_FOLLOW
			"notification_has_followed",		// Ram has followed you
			"notification_have_followed",		// Ram & Shayam have followed you
			"notification_others_have_followed"	// Ram, Shyam and 7 others have followed you
			),

	EMAIL ()
	;
	
	
	private String[] i18nIds;
	
	
	private I18nGroup( String ...i18nIds ) {
		this.i18nIds = i18nIds;
	}
	
}
