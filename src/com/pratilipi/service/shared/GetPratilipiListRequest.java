package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.PratilipiFilter;

public class GetPratilipiListRequest implements IsSerializable {

	private PratilipiFilter pratilipiFilter;
	
	private String cursor;
	
	private int resultCount;
	
	
	@SuppressWarnings("unused")
	private GetPratilipiListRequest() {}

	public GetPratilipiListRequest(
			PratilipiFilter pratilipiFilter, String cursor, int resultCount ) {
		
		this.pratilipiFilter = pratilipiFilter;
		this.cursor = cursor;
		this.resultCount = resultCount;
	}

	
	public PratilipiFilter getPratilipiFilter() {
		return pratilipiFilter;
	}

	public String getCursor() {
		return cursor;
	}
	
	public int getResultCount() {
		return resultCount;
	}

}
