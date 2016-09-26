package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiContentImageResponse;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
import com.pratilipi.api.shared.GenericFileUploadRequest;
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

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		private Integer pageNo;

		private String name;

		private Integer width;

	}

	public class PostRequest extends GenericFileUploadRequest {

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		@Validate( required = true, minInt = 1, requiredErrMsg = ERR_PRATILIPI_PAGE_NO_REQUIRED )
		private Integer pageNo;

		private PratilipiContentType contentType;

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
			PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiContentType contentType = request.contentType;
		if( contentType == null )
			contentType = DataAccessorFactory.getDataAccessor()
					.getPratilipi( request.pratilipiId )
					.getContentType();

		if( contentType == PratilipiContentType.PRATILIPI ) {
			BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
			blobEntry.setData( request.getData() );
			blobEntry.setMimeType( request.getMimeType() );
			blobEntry.setMetaName( request.getName() );
			String imageUrl = PratilipiDataUtil.createNewImage( request.pratilipiId, request.pageNo, blobEntry );

		} else if( contentType == PratilipiContentType.IMAGE ) {
			BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
			blobEntry.setData( request.getData() );
			blobEntry.setMimeType( request.getMimeType() );

			int pageCount = PratilipiDataUtil.updatePratilipiContent(
					request.pratilipiId,
					request.pageNo,
					PratilipiContentType.IMAGE,
					blobEntry,
					false );

			return new PostPratilipiContentImageResponse( request.pageNo, pageCount );

		}
		
		return null;
	}		

}
