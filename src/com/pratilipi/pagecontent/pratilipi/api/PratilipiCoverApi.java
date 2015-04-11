package com.pratilipi.pagecontent.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Post;
import com.claymus.api.shared.GenericFileDownloadResponse;
import com.claymus.api.shared.GenericResponse;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.BlobEntry;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiCoverRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.PostPratilipiCoverRequest;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/cover" )
public class PratilipiCoverApi extends GenericApi {

	@Get
	public GenericFileDownloadResponse getPratilipiCover( GetPratilipiCoverRequest request )
			throws UnexpectedServerException {

		BlobEntry blobEntry = (BlobEntry) PratilipiContentHelper.getPratilipiCover(
				request.getPratilipiId(),
				request.getWidth(),
				this.getThreadLocalRequest() );
		
		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag() );
	}

	@Post
	public GenericResponse postPratilipiCover( PostPratilipiCoverRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
		blobEntry.setData( request.getData() );
		blobEntry.setMimeType( request.getMimeType() );
		blobEntry.setCacheControl( "public, max-age=31536000" );
		
		PratilipiContentHelper.savePratilipiCover(
				request.getPratilipiId(),
				blobEntry,
				this.getThreadLocalRequest() );

		return new GenericResponse();
	}		

}
