package com.pratilipi.api.impl.init;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.util.InitDataUtil;


@SuppressWarnings("serial")
@Bind( uri = "/init/banner" )
public class InitBannerApi extends GenericApi {
	
	public static class GetRequest extends GenericRequest {
		
		@Validate( required = true )
		private Language language;
		
		@Validate( required = true )
		private String bannerId;
		
		private Integer width;
		
	}
	
	
	@Get
	public GenericResponse get( GetRequest request ) throws InvalidArgumentException, UnexpectedServerException {
		
		BlobEntry blobEntry = InitDataUtil.getHomeBanner(
				request.language,
				request.bannerId,
				request.width );
		
		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag()	);
		
	}
	
}
