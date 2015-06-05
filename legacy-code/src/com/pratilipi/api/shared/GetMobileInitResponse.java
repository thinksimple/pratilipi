package com.pratilipi.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.PratilipiData;

@SuppressWarnings( "serial" )
public class GetMobileInitResponse extends GenericResponse {

	// TODO : PROPER NAMING BASED ON USE OF THE LIST
	List<PratilipiData> topReadPratilipiDataList;
	
	public GetMobileInitResponse( List<PratilipiData> topReadPratilipiDataList ){
		this.topReadPratilipiDataList = topReadPratilipiDataList;
	}

}
