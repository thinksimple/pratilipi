package com.pratilipi.api.impl.pratilipi;

import com.google.gson.JsonArray;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.util.PratilipiDocUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content/chapter/delete" )
public class PratilipiContentChapterDeleteApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED, minLong = 1L )
		private Long pratilipiId;

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_CHAPTER_NO_REQUIRED, minInt = 1 )
		private int chapterNo;

	}

	
	@Post
	public PratilipiContentIndexApi.Response post( PostRequest request ) 
			throws InsufficientAccessException, UnexpectedServerException {

		JsonArray index = PratilipiDocUtil.deleteContentChapter(
				request.pratilipiId,
				request.chapterNo );
		return new PratilipiContentIndexApi.Response( index );

	}

}
