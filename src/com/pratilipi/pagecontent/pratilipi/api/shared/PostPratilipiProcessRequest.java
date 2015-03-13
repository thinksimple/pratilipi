package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PostPratilipiProcessRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;

	private Boolean processData;
	private Boolean processContent;
	private Boolean updateStats;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public boolean processData() {
		return processData == null ? false : processData;
	}
	
	public boolean processContent() {
		return processContent == null ? false : processContent;
	}

	public boolean updateStats() {
		return updateStats == null ? false : updateStats;
	}

}
