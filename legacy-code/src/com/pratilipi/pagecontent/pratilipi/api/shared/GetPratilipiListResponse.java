package com.pratilipi.pagecontent.pratilipi.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.PratilipiData;

@SuppressWarnings("serial")
public class GetPratilipiListResponse extends GenericResponse { 
	
	@SuppressWarnings("unused")
	private List<PratilipiData> pratilipiList;
	
	@SuppressWarnings("unused")
	private String cursor;

	
	@SuppressWarnings("unused")
	private GetPratilipiListResponse() {}
	
	public GetPratilipiListResponse( List<PratilipiData> pratilipiList, String cursor ) {
		this.pratilipiList = pratilipiList;
		this.cursor = cursor;
	}
	
}
