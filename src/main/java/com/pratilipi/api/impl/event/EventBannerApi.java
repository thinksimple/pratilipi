package com.pratilipi.api.impl.event;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.event.shared.GetEventBannerRequest;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.util.EventDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/event/banner" )
public class EventBannerApi extends GenericApi {

	@Get
	public GenericFileDownloadResponse get( GetEventBannerRequest request )
			throws UnexpectedServerException {

		BlobEntry blobEntry = EventDataUtil.getEventBanner(
				request.getEventId(),
				request.getWidth() );
		
		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag() );
		
	}

}
