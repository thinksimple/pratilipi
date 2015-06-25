package com.pratilipi.api.pratilipi.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.PratilipiData;

@SuppressWarnings("unused")
public class GetPratilipiListResponse extends GenericResponse { 
	
	private List<PratilipiData> pratilipiList;
	private String cursor;

	
	private GetPratilipiListResponse() {}
	
	public GetPratilipiListResponse( List<PratilipiData> pratilipiList, String cursor ) {
		this.pratilipiList = pratilipiList;
		this.cursor = cursor;
	}
	
}
