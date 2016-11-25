package com.pratilipi.api.impl.userauthor;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.util.UserAuthorDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/userauthor/email" )
public class UserAuthorEmailApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		@Validate( minLong = 1L )
		private Long userId;

		@Validate( minLong = 1L )
		private Long pratilipiId;

		private boolean sendContentPublishedMail;

	}


	@Post
	public GenericResponse post( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		JsonObject errorMessages = new JsonObject();

		if( request.sendContentPublishedMail && request.pratilipiId == null )
			errorMessages.addProperty( "pratilipiId", GenericRequest.ERR_PRATILIPI_ID_REQUIRED );

		if( request.sendContentPublishedMail && request.userId == null )
			errorMessages.addProperty( "userId", GenericRequest.ERR_USER_ID_REQUIRED );

		if( errorMessages.entrySet().size() > 0 )
			throw new InvalidArgumentException( errorMessages );


		if( request.sendContentPublishedMail )
			UserAuthorDataUtil.sendContentPublishedMail( request.userId, request.pratilipiId );
		
		// TODO: Mark it true in Notification Table

		return new GenericResponse();

	}

}
