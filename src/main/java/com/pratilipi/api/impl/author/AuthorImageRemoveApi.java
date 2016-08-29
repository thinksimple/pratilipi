package com.pratilipi.api.impl.author;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericFileUploadRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.util.AuthorDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/author/image/remove" )
public class AuthorImageRemoveApi extends GenericApi {

	public static class PostRequest extends GenericFileUploadRequest {

		@Validate( required = true, minLong = 1L )
		private Long authorId;

	}

	
	@Post
	public AuthorApi.Response post( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		AuthorData authorData = new AuthorData( request.authorId );
		authorData.setCustomImage( false );
		
		authorData = AuthorDataUtil.saveAuthorData( authorData );
		
		return new AuthorApi.Response( authorData, AuthorImageRemoveApi.class );
		
	}		

}
