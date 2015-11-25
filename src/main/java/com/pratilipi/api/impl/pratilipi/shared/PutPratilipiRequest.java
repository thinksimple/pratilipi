package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PutPratilipiRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;
	
	private String index;
	private boolean hasIndex;
	
	private String summary;
	private boolean hasSummary;

	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public String getIndex() {
		return index;
	}
	
	public boolean hasIndex() {
		return hasIndex;
	}

	public String getSummary() {
		return summary;
	}
	
	public boolean hasSummary() {
		return hasSummary;
	}

}
