package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class GetPratilipiContentResponse extends GenericResponse { 
	
	@SuppressWarnings("unused")
	private Long pratilipiId;
	
	@SuppressWarnings("unused")
	private Integer pageNo;
	
	@SuppressWarnings("unused")
	private String pageContent;

	
	@SuppressWarnings("unused")
	private GetPratilipiContentResponse() {}
	
	public GetPratilipiContentResponse( Long pratilipiId, Integer pageNo, String pageContent ) {
		this.pratilipiId = pratilipiId;
		this.pageNo = pageNo;
		this.pageContent = pageContent;
	}

}
