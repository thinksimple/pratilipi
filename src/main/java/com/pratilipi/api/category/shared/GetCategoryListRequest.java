package com.pratilipi.api.category.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Language;

public class GetCategoryListRequest extends GenericRequest {
	
	@Validate( required = true )
	private Language language;
	
	
	public Language getLanguage() {
		return this.language;
	}
	
}
