package com.pratilipi.api.pratilipi.shared;

import com.pratilipi.api.shared.GenericResponse;

@SuppressWarnings("unused")
public class PostPratilipiContentImageResponse extends GenericResponse {
	
	private Integer pageNo;
	private Integer pageCount;

	
	private PostPratilipiContentImageResponse() {}
	
	public PostPratilipiContentImageResponse( Integer pageNo, Integer pageCount ) {
		this.pageNo = pageNo;
		this.pageCount = pageCount;
	}
	
}