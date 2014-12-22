package com.pratilipi.pagecontent.pratilipi.shared;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.commons.shared.PratilipiContentType;

@SuppressWarnings("serial")
public class GetPratilipiContentResponse extends GenericResponse { 
	
	private Long pratilipiId;
	private Integer pageNo;
	private PratilipiContentType contentType;
	private String pageContent;

	
	@SuppressWarnings("unused")
	private GetPratilipiContentResponse() {}
	
	public GetPratilipiContentResponse(
			Long pratilipiId, Integer pageNo, PratilipiContentType contentType,
			String pageContent ) {
		
		this.pratilipiId = pratilipiId;
		this.pageNo = pageNo;
		this.contentType = contentType;
		this.pageContent = pageContent;
	}
	
	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Integer getPageNumber() {
		return pageNo;
	}
	
	public PratilipiContentType getContentType() {
		return contentType;
	}
	
	public Object getPageContent() {
		return pageContent;
	}

}
