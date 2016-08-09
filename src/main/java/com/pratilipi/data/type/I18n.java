package com.pratilipi.data.type;

import com.pratilipi.common.type.Language;

public interface I18n extends GenericOfyType {
	
	String getId();
	
	String getI18nString( Language language );
	
	void setI18nString( Language language, String i18nString );
	
}
