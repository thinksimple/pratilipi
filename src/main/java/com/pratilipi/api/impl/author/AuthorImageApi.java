package com.pratilipi.api.impl.author;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.author.shared.PostAuthorImageRequest;
import com.pratilipi.api.impl.pratilipi.shared.GetAuthorImageRequest;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
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

	@Get
	public GenericFileDownloadResponse getAuthorImage( GetAuthorImageRequest request )
			throws UnexpectedServerException {

		BlobEntry blobEntry = (BlobEntry) AuthorDataUtil.getAuthorImage(
				request.getAuthorId(),
				request.getWidth() );
		
		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag() );
	}

	@Post
	public GenericResponse postAuthorImage( PostAuthorImageRequest request )
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
				request.getAuthorId(),
				blobEntry );

		return new GenericResponse();
	}		

}
