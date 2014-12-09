package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.PratilipiContentType;

public class GetPratilipiContentResponse implements IsSerializable { 
	
	private long pratilipiId;
	private int pageNo;
	private PratilipiContentType contentType;
	private Object pageContent;
	private String pageContentMimeType;

	
	@SuppressWarnings("unused")
	private GetPratilipiContentResponse() {}
	
	public GetPratilipiContentResponse(
			long pratilipiId, int pageNo, PratilipiContentType contentType,
			Object pageContent, String pageContentMimeType ) {
		
		this.pratilipiId = pratilipiId;
		this.pageNo = pageNo;
		this.contentType = contentType;
		this.pageContent = pageContent;
		this.pageContentMimeType = pageContentMimeType;
	}
	
	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public int getPageNumber() {
		return pageNo;
	}
	
	public PratilipiContentType getContentType() {
		return contentType;
	}
	
	public Object getPageContent() {
		return pageContent;
	}

	public String getPageContentMimeType() {
		return pageContentMimeType;
	}

}
