package com.pratilipi.pagecontent.author.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericFileUploadRequest;

@SuppressWarnings("serial")
public class PostAuthorImageRequest extends GenericFileUploadRequest {

	@Validate( required = true )
	private Long authorId;
	

	public Long getAuthorId() {
		return authorId;
	}

}
