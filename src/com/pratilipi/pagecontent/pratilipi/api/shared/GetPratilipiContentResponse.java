package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.commons.shared.PratilipiContentType;

@SuppressWarnings("serial")
public class GetPratilipiContentResponse extends GenericResponse { 
	
	@SuppressWarnings("unused")
	private Long pratilipiId;
	
	@SuppressWarnings("unused")
	private Integer pageNo;
	
	@SuppressWarnings("unused")
	private PratilipiContentType contentType;
	
	@SuppressWarnings("unused")
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

}
