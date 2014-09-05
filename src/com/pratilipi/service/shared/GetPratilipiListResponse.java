package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.PratilipiData;

public class GetPratilipiListResponse implements IsSerializable {

	private List<PratilipiData> pratilipiDataList;
	
	
	@SuppressWarnings("unused")
	private GetPratilipiListResponse() {}
	
	public GetPratilipiListResponse( List<PratilipiData> pratilipiDataList ) {
		this.pratilipiDataList = pratilipiDataList;
	}
	
	
	public List<PratilipiData> getPratilipiDataList() {
		return pratilipiDataList;
	}
	
}
