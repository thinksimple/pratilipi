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
			throws InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
		blobEntry.setData( request.getData() );
		blobEntry.setMimeType( request.getMimeType() );
		
		PratilipiDataUtil.savePratilipiCover(
				request.getPratilipiId(),
				blobEntry );

		return new GenericResponse();
	}		

}
