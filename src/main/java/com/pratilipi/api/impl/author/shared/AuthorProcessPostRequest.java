package com.pratilipi.api.impl.author.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class AuthorProcessPostRequest extends GenericRequest {

	@Validate( required = true )
	private Long authorId;

	private Boolean validateData;
	private Boolean processData;
	private Boolean processImage;
	private Boolean updateStats;

	
	public Long getAuthorId() {
		return authorId;
	}

	
	public boolean validateData() {
		return validateData == null ? false : validateData;
	}

	public boolean processData() {
		return processData == null ? false : processData;
	}
	
	public boolean processImage() {
		return processImage == null ? false : processImage;
	}
	
	public boolean updateStats() {
		return updateStats == null ? false : updateStats;
	}

}
