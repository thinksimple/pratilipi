package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetPratilipiContentResponse implements IsSerializable { 
	
	private long pratilipiId;
	private int pageNo;
	private String pageContent;

	
	@SuppressWarnings("unused")
	private GetPratilipiContentResponse() {}
	
	public GetPratilipiContentResponse( long pratilipiId, int pageNo, String pageContent ) {
		this.pratilipiId = pratilipiId;
		this.pageNo = pageNo;
		this.pageContent = pageContent;
	}
	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public int getPageNumber() {
		return pageNo;
	}
	
	public String getPageContent() {
		return this.pageContent;
	}

}
