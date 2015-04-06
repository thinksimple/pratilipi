package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class PutUserPratilipiRequest extends GenericRequest {
	
	@Validate( required = true )
	private Long pratilipiId;		
	
	private Boolean hasBookmark;
	private Integer bookmark;
	private String requestType;
	
	
	public Long getPratilipiId(){
		return pratilipiId;
	}
	
	public Boolean hasBookmark(){
		return hasBookmark;
	}
	
	public Integer getBookmark(){
		return bookmark;
	}
	
	public String getRequestType(){
		return requestType;
	}
}
