package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class GetPratilipiRequest extends GenericRequest {

	@Validate( required=true )
	private Long pratilipiId;
	
	public Long getPratilipiId(){
		return pratilipiId;
	}
}
