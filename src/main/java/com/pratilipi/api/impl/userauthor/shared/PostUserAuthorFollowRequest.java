package com.pratilipi.api.impl.userauthor.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserAuthorFollowRequest extends GenericRequest {

	@Validate( required = true, minLong = 1L )
	private Long authorId;
	
	@Validate( required = true )
	private Boolean following;
	
	
	public Long getAuthorId() {
		return authorId;
	}
	
	public Boolean getFollowing() {
		return following;
	}

}
