package com.pratilipi.api.impl.pratilipi.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericRequest;

public class PostPratilipiProcessRequest extends GenericRequest {

	private Long pratilipiId;
	private List<Long> pratilipiIdList;

	private Boolean processData;
	private Boolean processCover;
	private Boolean processContent;
	private Boolean updateStats;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public List<Long> getPratilipiIdList() {
		return pratilipiIdList;
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
