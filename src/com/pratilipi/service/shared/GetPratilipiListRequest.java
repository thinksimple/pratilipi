package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.PratilipiType;

public class GetPratilipiListRequest implements IsSerializable {

	private PratilipiType pratilipiType;
	
	private String cursor;
	
	private int resultCount;
	
	
	@SuppressWarnings("unused")
	private GetPratilipiListRequest() {}

	public GetPratilipiListRequest(
			PratilipiType pratilipiType,
			String cursor, int resultCount ) {
		
		this.pratilipiType = pratilipiType;
		this.cursor = cursor;
		this.resultCount = resultCount;
	}

	
	public PratilipiType getPratilipiType() {
		return pratilipiType;
	}

	public String getCursor() {
		return cursor;
	}
	
	public int getResultCount() {
		return resultCount;
	}

}
