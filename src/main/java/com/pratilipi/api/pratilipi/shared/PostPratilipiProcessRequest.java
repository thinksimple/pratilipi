package com.pratilipi.api.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostPratilipiProcessRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;

	private Boolean processData;
	private Boolean processCover;
	private Boolean processContent;
	private Boolean updateStats;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public boolean processData() {
		return processData == null ? false : processData;
	}
	
	public boolean processCover() {
		return processCover == null ? false : processCover;
	}
	
	public boolean processContent() {
		return processContent == null ? false : processContent;
	}

	public boolean updateStats() {
		return updateStats == null ? false : updateStats;
	}

}
