package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.shared.GenericResponse;

@SuppressWarnings("unused")
public class GetPratilipiContentResponse extends GenericResponse { 
	
	private Long pratilipiId;
	private Integer pageNo;
	private Object pageContent;

	
	private GetPratilipiContentResponse() {}
	
	public GetPratilipiContentResponse( Long pratilipiId, Integer pageNo, Object pageContent ) {
		this.pratilipiId = pratilipiId;
		this.pageNo = pageNo;
		this.pageContent = pageContent;
	}

}
