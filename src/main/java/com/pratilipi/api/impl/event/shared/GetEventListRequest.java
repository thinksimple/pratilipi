package com.pratilipi.api.impl.event.shared;

import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Language;

public class GetEventListRequest extends GenericRequest {

	private Language language;
	
	
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage( Language language ) {
		this.language = language;
	}

}
