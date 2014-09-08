package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.PratilipiData;

public class GetPratilipiListResponse implements IsSerializable {

	private List<PratilipiData> pratilipiDataList;
	
	private String cursor;

	
	@SuppressWarnings("unused")
	private GetPratilipiListResponse() {}
	
	public GetPratilipiListResponse(
			List<PratilipiData> pratilipiDataList,
			String cursor ) {
		
		this.pratilipiDataList = pratilipiDataList;
		this.cursor = cursor;
	}
	
	
	public List<PratilipiData> getPratilipiDataList() {
		return pratilipiDataList;
	}

	public String getCursor() {
		return cursor;
	}
	
}
