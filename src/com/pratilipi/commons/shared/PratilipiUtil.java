package com.pratilipi.commons.shared;

import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;

public class PratilipiUtil {

	public static String createLanguageName( LanguageData languageData ) {
		return languageData.getName() + " (" + languageData.getNameEn() + ")";
	}

	public static String createAuthorName( AuthorData authorData ) {
		return authorData.getFirstName()
				+ ( authorData.getLastName() == null ? "" : " " + authorData.getLastName() )
				+ " ("
				+ authorData.getFirstNameEn()
				+ ( authorData.getLastNameEn() == null ? "" : " " + authorData.getLastNameEn() )
				+ ")";
	}
	
}
