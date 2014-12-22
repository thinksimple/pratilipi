package com.pratilipi.pagecontent.pratilipi.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;
import com.pratilipi.commons.shared.PratilipiContentType;

@SuppressWarnings("serial")
public class GetPratilipiContentRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;

	@Validate( required = true )
	private Integer pageNo;
	
	@Validate( required = true )
	private PratilipiContentType contentType;
	

	private GetPratilipiContentRequest() {
		super( null );
	}
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Integer getPageNumber() {
		return pageNo;
	}
	
	public PratilipiContentType getContentType() {
		return contentType;
	}

}
