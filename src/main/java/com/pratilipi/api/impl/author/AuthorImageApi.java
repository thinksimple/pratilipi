package com.pratilipi.api.impl.author;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
import com.pratilipi.api.shared.GenericFileUploadRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.util.AuthorDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/author/image" )
public class AuthorImageApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		private Long authorId;

		private Integer width;
		
	}
	
	public static class PostRequest extends GenericFileUploadRequest {

		@Validate( required = true )
		private Long authorId;

	}

	
	@Get
	public GenericFileDownloadResponse get( GetRequest request )
			throws UnexpectedServerException {

		BlobEntry blobEntry = (BlobEntry) AuthorDataUtil.getAuthorImage(
				request.authorId,
				request.width );
		
		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag() );
		
	}

	@Post
	public GenericResponse post( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		// This is to cover issue with jQuery FileUpload plug-in.
		// When user hits "Ctrl+v", the plug-in uploads the data in clipboard as
		// an image with file name "undefined".
		// This can be removed as soon as Mark-4 is deprecated.
		if( request.getName() != null && request.getName().equals( "undefined" ) )
			throw new InvalidArgumentException( "File name 'undefined' is not allowed." );
		
		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
		blobEntry.setData( request.getData() );
		blobEntry.setMimeType( request.getMimeType() );
		
		AuthorDataUtil.saveAuthorImage(
				request.authorId,
				blobEntry );

		return new GenericResponse();
		
	}		

}
