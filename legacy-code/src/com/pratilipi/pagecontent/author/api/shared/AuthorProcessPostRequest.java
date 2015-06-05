package com.pratilipi.pagecontent.author.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class AuthorProcessPostRequest extends GenericRequest {

	@Validate( required = true )
	private Long authorId;

	private Boolean processData;
	private Boolean processImage;
	private Boolean updateStats;
	

	public Long getAuthorId() {
		return authorId;
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
