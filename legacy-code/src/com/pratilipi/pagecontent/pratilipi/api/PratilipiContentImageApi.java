package com.pratilipi.pagecontent.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Post;
import com.claymus.api.shared.GenericFileDownloadResponse;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.BlobEntry;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiContentImageRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.PostPratilipiContentImageRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.PostPratilipiContentImageResponse;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content/image" )
public class PratilipiContentImageApi extends GenericApi {

	@Get
	public GenericFileDownloadResponse getPratilipiContent( GetPratilipiContentImageRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = (BlobEntry) PratilipiContentHelper.getPratilipiContent(
				request.getPratilipiId(),
				request.getPageNumber(),
				PratilipiContentType.IMAGE,
				this.getThreadLocalRequest() );
		
		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag() );
	}

	@Post
	public PostPratilipiContentImageResponse postPratilipiContent( PostPratilipiContentImageRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
		blobEntry.setData( request.getData() );
		blobEntry.setMimeType( request.getMimeType() );
		
		int pageCount = PratilipiContentHelper.updatePratilipiContent(
				request.getPratilipiId(),
				request.getPageNumber(),
				PratilipiContentType.IMAGE,
				blobEntry,
				false,
				this.getThreadLocalRequest() );
			
		return new PostPratilipiContentImageResponse( request.getPageNumber(), pageCount );
	}		

}
