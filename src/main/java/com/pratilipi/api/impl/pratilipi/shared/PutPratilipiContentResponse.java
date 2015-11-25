package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.shared.GenericResponse;

public class PutPratilipiContentResponse extends GenericResponse {
	
	@SuppressWarnings("unused")
	private Integer pageNo;

	@SuppressWarnings("unused")
	private Integer pageCount;

	
	@SuppressWarnings("unused")
	private PutPratilipiContentResponse() {}
	
	public PutPratilipiContentResponse( Integer pageNo, Integer pageCount ) {
		this.pageNo = pageNo;
		this.pageCount = pageCount;
	}
	
}
