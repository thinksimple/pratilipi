package com.pratilipi.api.impl.userpratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserPratilipiLibraryRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;
	
	@Validate( required = true )
	private Boolean addedToLib;

	
	public Long getPratilipiId() {
		return pratilipiId;
	}
	
	public Boolean isAddedToLib() {
		return addedToLib == null ? false : addedToLib;
	}

}
