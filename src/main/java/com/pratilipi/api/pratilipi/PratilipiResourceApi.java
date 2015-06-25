package com.pratilipi.api.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.pratilipi.shared.GetPratilipiResourceRequest;
import com.pratilipi.api.pratilipi.shared.PostPratilipiResourceRequest;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/resource" )
public class PratilipiResourceApi extends GenericApi {

	@Get
	public GenericFileDownloadResponse getPratilipiContent( GetPratilipiResourceRequest request )
			throws UnexpectedServerException {

		BlobEntry blobEntry = (BlobEntry) PratilipiDataUtil.getPratilipiResource(
				request.getPratilipiId(),
				request.getName() );
		
		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag() );
	}

	@Post
	public GenericResponse postPratilipiResource( PostPratilipiResourceRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
		blobEntry.setData( request.getData() );
		blobEntry.setMimeType( request.getMimeType() );
		
		@SuppressWarnings("unused")
		boolean success = PratilipiDataUtil.savePratilipiResource(
				request.getPratilipiId(),
				blobEntry,
				request.getOverWrite() );

		return new GenericResponse();
	}

}
