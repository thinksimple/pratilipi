package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class PutUserPratilipiRequest extends GenericRequest {
	
	@Validate( required = true, minLong = 1 )
	private Long pratilipiId;		
	
	private Integer bookmark;
	private boolean hasBookmark;

	private String requestType;
	
	
	public Long getPratilipiId() {
		return pratilipiId;
	}
	
	public Integer getBookmark(){
		return bookmark;
	}

	public Boolean hasBookmark() {
		return hasBookmark;
	}
	
	public String getRequestType() {
		return requestType;
	}
	
}
