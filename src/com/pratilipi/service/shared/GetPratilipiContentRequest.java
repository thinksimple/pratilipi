package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetPratilipiContentRequest implements IsSerializable {

	private long pratilipiId;
	private int pageNo;


	@SuppressWarnings("unused")
	private GetPratilipiContentRequest() {}

	public GetPratilipiContentRequest( long pratilipiId, int pageNo ) {
		this.pratilipiId = pratilipiId;
		this.pageNo = pageNo;
	}

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public int getPageNumber() {
		return pageNo;
	}

}
