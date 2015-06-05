package com.pratilipi.pagecontent.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Post;
import com.claymus.api.shared.GenericFileDownloadResponse;
import com.claymus.api.shared.GenericHtmlResponse;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.BlobEntry;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiResourceRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.PostPratilipiResourceRequest;

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
	public GenericHtmlResponse postPratilipiResource( PostPratilipiResourceRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
		blobEntry.setData( request.getData() );
		blobEntry.setMimeType( request.getMimeType() );
		
		boolean success = PratilipiContentHelper.savePratilipiResource(
				request.getPratilipiId(),
				blobEntry,
				request.getOverWrite(),
				this.getThreadLocalRequest() );

		String url = success ? "/api.pratilipi/pratilipi/resource?pratilipiId=" + request.getPratilipiId() + "&name=" + request.getName() : "";
		String msg = success ? "" : "File with this name already exists on server.";
		
		return new GenericHtmlResponse( "<html><body><script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction( '1', '" + url + "', '" + msg + "');</script></body></html>" );
	}		

}
