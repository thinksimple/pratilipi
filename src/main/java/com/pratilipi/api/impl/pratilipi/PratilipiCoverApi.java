package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiCoverRequest;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiCoverRequest;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/cover" )
public class PratilipiCoverApi extends GenericApi {

	@Get
	public GenericFileDownloadResponse getPratilipiCover( GetPratilipiCoverRequest request )
			throws UnexpectedServerException {

		BlobEntry blobEntry = (BlobEntry) PratilipiDataUtil.getPratilipiCover(
				request.getPratilipiId(),
				request.getWidth() );
		
		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag() );
		
	}

	@Post
	public GenericResponse postPratilipiCover( PostPratilipiCoverRequest request )
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
		
		PratilipiDataUtil.savePratilipiCover(
				request.getPratilipiId(),
				blobEntry );

		return new GenericResponse();
		
	}		

}
