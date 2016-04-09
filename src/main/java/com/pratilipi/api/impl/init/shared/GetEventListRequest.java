package com.pratilipi.api.impl.init.shared;

import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Language;

public class GetEventListRequest extends GenericRequest {

	private Language language;
	
	
	public Language getLanguage() {
		return language;
	}
	
}
