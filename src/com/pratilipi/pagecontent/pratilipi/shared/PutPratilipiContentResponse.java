package com.pratilipi.pagecontent.pratilipi.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
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
