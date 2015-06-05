package com.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class GetMobileInitRequest extends GenericRequest {

	@Validate( required=true )
	private Long languageId;
	
	public Long getLanguageId(){
		return languageId;
	}
}
