package com.pratilipi.api.pratilipi.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetAuthorImageRequest extends GenericRequest {

	private Long authorId;

	private Integer width;
	

	public Long getAuthorId() {
		return authorId;
	}

	public Integer getWidth() {
		return width;
	}
	
}
