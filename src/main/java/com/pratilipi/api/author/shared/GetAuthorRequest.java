package com.pratilipi.api.author.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class GetAuthorRequest extends GenericRequest {
	
	@Validate( required = true )
	private Long authorId;
	
	
	public Long getAuthorId() {
		return authorId;
	}

}
