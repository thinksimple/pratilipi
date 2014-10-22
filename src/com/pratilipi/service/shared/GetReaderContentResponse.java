package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetReaderContentResponse implements IsSerializable { 
	
	private String pageContent;
	
	private Boolean isLastPage; 
	
	@SuppressWarnings("unused")
	private GetReaderContentResponse() {}
	
	public GetReaderContentResponse( String pageContent, Boolean isLastPage ) {
		this.pageContent = pageContent;
		this.isLastPage = isLastPage;
	}
	
	public String getPageContent() {
		return this.pageContent;
	}
	
	public Boolean isLastPage() {
		return this.isLastPage;
	}
	
}
