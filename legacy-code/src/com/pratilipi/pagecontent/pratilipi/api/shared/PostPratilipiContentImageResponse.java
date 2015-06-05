package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class PostPratilipiContentImageResponse extends GenericResponse {
	
	@SuppressWarnings("unused")
	private Integer pageNo;

	@SuppressWarnings("unused")
	private Integer pageCount;

	
	@SuppressWarnings("unused")
	private PostPratilipiContentImageResponse() {}
	
	public PostPratilipiContentImageResponse( Integer pageNo, Integer pageCount ) {
		this.pageNo = pageNo;
		this.pageCount = pageCount;
	}
	
}