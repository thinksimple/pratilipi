package com.pratilipi.api.impl.userauthor;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.userauthor.shared.PostUserAuthorFollowRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.client.UserAuthorData;
import com.pratilipi.data.util.UserAuthorDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/userauthor/follow" )
public class UserAuthorFollowApi extends GenericApi {

	@Post
	public GenericResponse get( PostUserAuthorFollowRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		UserAuthorData userAuthorData =  new UserAuthorData();
		userAuthorData.setUserId( AccessTokenFilter.getAccessToken().getUserId() );
		userAuthorData.setAuthorId( request.getAuthorId() );
		userAuthorData.setFollowing( request.getFollowing() );
		
		userAuthorData = UserAuthorDataUtil.saveUserAuthor( userAuthorData );
		
		return new GenericResponse();
	
	}

}
