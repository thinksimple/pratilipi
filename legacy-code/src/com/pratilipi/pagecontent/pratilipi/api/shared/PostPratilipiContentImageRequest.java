package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericFileUploadRequest;

@SuppressWarnings("serial")
public class PostPratilipiContentImageRequest extends GenericFileUploadRequest {

	@Validate( required = true )
	private Long pratilipiId;

	@Validate( required = true )
	private Integer pageNo;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Integer getPageNumber() {
		return pageNo;
	}
	
}
