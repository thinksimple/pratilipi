package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.PratilipiContentType;

public class GetPratilipiContentRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;

	@Validate( required = true )
	private Integer chapterNo;
	
	@Validate( required = true )
	private Integer pageNo;
	
	@Validate
	private String pageletId;
	
	private PratilipiContentType contentType;

	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Integer getChapterNo() {
		return chapterNo;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}
	
	public Integer getPageletId() {
		return pageNo;
	}
	
	public PratilipiContentType getContentType() {
		return contentType;
	}
	
}
