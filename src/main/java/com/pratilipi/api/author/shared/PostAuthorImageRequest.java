package com.pratilipi.api.author.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericFileUploadRequest;

public class PostAuthorImageRequest extends GenericFileUploadRequest {

	@Validate( required = true )
	private Long authorId;
	

	public Long getAuthorId() {
		return authorId;
	}

}
