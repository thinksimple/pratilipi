package com.pratilipi.api.author;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.author.shared.PostAuthorImageRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.util.AuthorDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/author/image" )
public class AuthorImageApi extends GenericApi {

	@Post
	public GenericResponse postAuthorImage( PostAuthorImageRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
		blobEntry.setData( request.getData() );
		blobEntry.setMimeType( request.getMimeType() );
		
		AuthorDataUtil.saveAuthorImage(
				request.getAuthorId(),
				blobEntry );

		return new GenericResponse();
	}		

}
