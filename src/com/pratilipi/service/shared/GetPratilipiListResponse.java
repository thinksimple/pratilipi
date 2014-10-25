package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.service.shared.data.PratilipiData;

public class GetPratilipiListResponse implements IsSerializable {

	private List<PratilipiData> pratilipiDataList;
	
	private PratilipiFilter pratilipiFilter;
	
	private String cursor;

	
	@SuppressWarnings("unused")
	private GetPratilipiListResponse() {}
	
	public GetPratilipiListResponse(
			List<PratilipiData> pratilipiDataList,
			PratilipiFilter pratilipiFilter, String cursor ) {
		
		this.pratilipiDataList = pratilipiDataList;
		this.pratilipiFilter = pratilipiFilter;
		this.cursor = cursor;
	}
	
	
	public List<PratilipiData> getPratilipiDataList() {
		return pratilipiDataList;
	}

	public PratilipiFilter getPratilipiFilter() {
		return pratilipiFilter;
	}
	
	public String getCursor() {
		return cursor;
	}
	
}
