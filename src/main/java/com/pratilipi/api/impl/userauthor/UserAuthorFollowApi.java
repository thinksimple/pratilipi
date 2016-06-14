package com.pratilipi.api.impl.userauthor;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.util.UserAuthorDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/userauthor/follow" )
public class UserAuthorFollowApi extends GenericApi {

	public class Request extends GenericRequest {

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
	
	
	@Post
	public GenericResponse post( Request request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		UserAuthorDataUtil.saveUserAuthorFollow(
				AccessTokenFilter.getAccessToken().getUserId(),
				request.authorId,
				request.following );
		
		return new GenericResponse();
	
	}

}
