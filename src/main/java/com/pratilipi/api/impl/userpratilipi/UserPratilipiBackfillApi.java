package com.pratilipi.api.impl.userpratilipi;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind(uri = "/userpratilipi/backfill")
public class UserPratilipiBackfillApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		@Validate(required = true, minLong = 1L)
		private Long pratilipiId;

		@Validate(required = true, minLong = 1L)
		private Long userId;

		@Validate(required = true)
		private JsonObject lastPageOpened;

	}

	@Post
	public GenericResponse post(PostRequest request) throws InsufficientAccessException, UnexpectedServerException {

		if (!hasAccess())
			throw new InsufficientAccessException();

		UserPratilipiData userPratilipiData = updateUserPratilipi(request.userId, request.pratilipiId,
				request.lastPageOpened.toString());

		return new GenericResponse();
	}

	private boolean hasAccess() throws InsufficientAccessException {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		
		if (accessToken == null || accessToken.getUserId() != 5073076857339904L)
			throw new InsufficientAccessException();

		return true;
	}

	private UserPratilipiData updateUserPratilipi(Long userId, Long pratilipiId, String lastPageOpened) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi(userId, pratilipiId);
		if (userPratilipi == null) {
			// CREATE USER PRATILIPI
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId(userId);
			userPratilipi.setPratilipiId(pratilipiId);
		}

		// update lastPageOpened
		userPratilipi.setLastOpenedPage(lastPageOpened);

		userPratilipi = dataAccessor.createOrUpdateUserPratilipi(userPratilipi, null);

		return null;
	}

}
