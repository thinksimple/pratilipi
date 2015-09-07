package com.pratilipi.api.pratilipi.shared;

import com.pratilipi.api.pratilipi.data.PratilipiCategory;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Language;

public class GetPratilipiCategoryRequest extends GenericRequest {
	
	private PratilipiCategory category;
	private Language language;
	
	public PratilipiCategory getCategory() {
		return this.category;
	}
	
	public void setCategory( PratilipiCategory category ) {
		this.category = category;
	}
	
	public Language getLanguage() {
		return this.language;
	}
	
	public void setLanguage( Language language ) {
		this.language = language;
	}
	
}
