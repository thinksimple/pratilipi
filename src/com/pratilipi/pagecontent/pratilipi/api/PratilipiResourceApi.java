package com.pratilipi.pagecontent.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Post;
import com.claymus.api.shared.GenericFileDownloadResponse;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.BlobEntry;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiResourceRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.PostPratilipiResourceRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.PostPratilipiResourceResponse;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/resource" )
public class PratilipiResourceApi extends GenericApi {

	@Get
	public GenericFileDownloadResponse getPratilipiContent( GetPratilipiResourceRequest request )
			throws UnexpectedServerException {

		BlobEntry blobEntry = (BlobEntry) PratilipiContentHelper.getPratilipiResource(
				request.getPratilipiId(),
				request.getName(),
				this.getThreadLocalRequest() );
		
		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag() );
	}

	@Post
	public PostPratilipiResourceResponse postPratilipiResource( PostPratilipiResourceRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
		blobEntry.setData( request.getData() );
		blobEntry.setMimeType( request.getMimeType() );
		
		PratilipiContentHelper.savePratilipiResource(
				request.getPratilipiId(),
				blobEntry,
				request.getOverWrite(),
				this.getThreadLocalRequest() );
			
		return new PostPratilipiResourceResponse();
	}		

}
