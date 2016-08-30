package com.pratilipi.api.impl.author;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericFileUploadRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.util.AuthorDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/author/cover/remove" )
public class AuthorCoverRemoveApi extends GenericApi {

	public static class PostRequest extends GenericFileUploadRequest {

		@Validate( required = true, minLong = 1L )
		private Long authorId;

	}

	
	@Post
	public GenericResponse post( PostRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		AuthorDataUtil.removeAuthorImage( request.authorId, true, false );
		return new GenericResponse();
		
	}		

}
