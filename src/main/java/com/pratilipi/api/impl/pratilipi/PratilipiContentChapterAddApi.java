package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.PratilipiContentUtil;
import com.pratilipi.data.type.PratilipiContentDoc;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content/chapter/add" )
public class PratilipiContentChapterAddApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		@Validate( minInt = 1 )
		private Integer chapterNo;

	}

	@Post
	public GenericResponse postAddChapter( PostRequest request )
			throws UnexpectedServerException {

		PratilipiContentDoc pcDoc = PratilipiContentUtil.addChapter( request.pratilipiId, request.chapterNo );
		return new PratilipiContentIndexApi.Response( pcDoc.getIndex() );

	}

}
