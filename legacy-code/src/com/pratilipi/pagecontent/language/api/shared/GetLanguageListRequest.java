package com.pratilipi.pagecontent.language.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class GetLanguageListRequest extends GenericRequest {
	
	private Boolean includeHidden;
	

	public Boolean includeHidden() {
		return includeHidden == null ? false : includeHidden;
	}

}
