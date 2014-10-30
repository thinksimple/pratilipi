package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.PratilipiData;

public class GetSearchResponse implements IsSerializable {

	private List<PratilipiData> pratilipiDataList;
	
	private Boolean loadFinished;

	
	@SuppressWarnings("unused")
	private GetSearchResponse() {}
	
	public GetSearchResponse(
			List<PratilipiData> pratilipiDataList,
			boolean loadFinished ) {
		
		this.pratilipiDataList = pratilipiDataList;
		this.loadFinished = loadFinished;
	}
	
	
	public List<PratilipiData> getPratilipiDataList() {
		return pratilipiDataList;
	}

	public Boolean isLoadFinished() {
		return loadFinished;
	}
	
}
