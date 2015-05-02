package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.PratilipiData;

@SuppressWarnings("serial")
public class GetPratilipiResponse extends GenericResponse {

	@SuppressWarnings( "unused" )
	private PratilipiData pratilipiData;
	
	public GetPratilipiResponse( PratilipiData pratilipiData ){
		this.pratilipiData = pratilipiData;
	}
	
}
