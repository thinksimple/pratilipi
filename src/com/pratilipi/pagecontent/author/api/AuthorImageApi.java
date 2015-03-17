package com.pratilipi.pagecontent.author.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Post;
import com.claymus.api.shared.GenericResponse;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.BlobEntry;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.author.api.shared.PostAuthorImageRequest;

@SuppressWarnings("serial")
@Bind( uri = "/author/image" )
public class AuthorImageApi extends GenericApi {

	@Post
	public GenericResponse postAuthorImage( PostAuthorImageRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
		blobEntry.setData( request.getData() );
		blobEntry.setMimeType( request.getMimeType() );
		
		AuthorContentHelper.saveAuthorImage(
				request.getAuthorId(),
				blobEntry,
				this.getThreadLocalRequest() );

		return new GenericResponse();
	}		

}
