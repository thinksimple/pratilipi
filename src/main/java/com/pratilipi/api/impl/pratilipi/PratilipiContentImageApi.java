package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiContentImageRequest;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiContentImageResponse;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content/image" )
public class PratilipiContentImageApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Long pratilipiId;

		private Integer pageNo;
		
		private String name;

		private Integer width;

	}
	
	
	@Get
	public GenericFileDownloadResponse getPratilipiContent( GetRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = null;
		
		if( request.pageNo != null )
			blobEntry = (BlobEntry) PratilipiDataUtil.getPratilipiContent(
					request.pratilipiId,
					0,
					request.pageNo,
					PratilipiContentType.IMAGE );
		
		else if( request.name != null )
			blobEntry = (BlobEntry) PratilipiDataUtil.getPratilipiContentImage(
					request.pratilipiId,
					request.name,
					request.width );
		
		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag() );
	
	}

	@Post
	public PostPratilipiContentImageResponse postPratilipiContent(
			PostPratilipiContentImageRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
		blobEntry.setData( request.getData() );
		blobEntry.setMimeType( request.getMimeType() );
		
		int pageCount = PratilipiDataUtil.updatePratilipiContent(
				request.getPratilipiId(),
				request.getPageNumber(),
				PratilipiContentType.IMAGE,
				blobEntry,
				false );
			
		return new PostPratilipiContentImageResponse( request.getPageNumber(), pageCount );
		
	}		

}
