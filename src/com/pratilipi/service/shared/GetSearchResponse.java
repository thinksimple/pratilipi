package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.PratilipiData;

public class GetSearchResponse implements IsSerializable {

	private List<PratilipiData> pratilipiDataList;
	
	private String serverMsg;
	
	private String cursor;

	
	@SuppressWarnings("unused")
	private GetSearchResponse() {}
	
	public GetSearchResponse(
			List<PratilipiData> pratilipiDataList,
			String serverMsg, String cursor ) {
		
		this.pratilipiDataList = pratilipiDataList;
		this.serverMsg = serverMsg;
		this.cursor = cursor;
	}
	
	
	public List<PratilipiData> getPratilipiDataList() {
		return pratilipiDataList;
	}

	public String getServerMsg() {
		return serverMsg;
	}
	
	public String getCursor(){
		return this.cursor;
	}
	
}
