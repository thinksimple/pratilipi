package com.pratilipi.data.type;

import com.pratilipi.common.type.Language;

public interface Tag extends GenericOfyType {
	
	Long getId();
	
	String getI18nName( Language language );
	
	void setI18nName( Language language, String i18nString );
	
}
