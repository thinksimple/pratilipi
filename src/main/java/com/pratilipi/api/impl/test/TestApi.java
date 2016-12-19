package com.pratilipi.api.impl.test;

import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.UserFollowState;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Long userId;

		public void setUserId( Long userId ) {
			this.userId = userId;
		}

	}

	@Get
	public GenericResponse get( GetRequest request ) {

		if( ! UserAccessUtil.hasUserAccess( AccessTokenFilter.getAccessToken().getUserId(), null, AccessType.USER_ADD ) )
			return new GenericResponse();

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		List<UserAuthor> userAuthorList = dataAccessor.getUserAuthorList( request.userId, null, null, null, null ).getDataList();

		for( UserAuthor userAuthor : userAuthorList ) {
			userAuthor.setState( userAuthor.isFollowing() ? UserFollowState.FOLLOWING : UserFollowState.UNFOLLOWED );
		}

		userAuthorList = dataAccessor.createOrUpdateUserAuthorList( userAuthorList );

		return new GenericResponse();

	}

}
