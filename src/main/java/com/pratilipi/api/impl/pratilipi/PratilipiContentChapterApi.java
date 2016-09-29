package com.pratilipi.api.impl.pratilipi;

import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.PratilipiContentDoc;
import com.pratilipi.data.util.PratilipiDocUtil;

@SuppressWarnings({ "unused", "serial" })
@Bind( uri = "/pratilipi/content/chapter" )
public class PratilipiContentChapterApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( PratilipiContentChapterApi.class.getName() );

	public static class PostRequest extends GenericRequest {

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED, minLong = 1L )
		private Long pratilipiId;

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_CHAPTER_NO_REQUIRED, minInt = 1 )
		private Integer chapterNo;

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_PAGE_NO_REQUIRED, minInt = 1 )
		private Integer pageNo;

		private String chapterTitle;

		private String content;

	}

	public static class Response extends GenericResponse {

		private Long pratilipiId;
		private Integer chapterNo;
		private Integer pageNo;
		private String chapterTitle;
		private Object content;

		private Response() {}

		private Response( Long pratilipiId, Integer chapterNo, Integer pageNo, String chapterTitle, Object content ) {
			this.pratilipiId = pratilipiId;
			this.chapterNo = chapterNo;
			this.pageNo = pageNo;
			this.chapterTitle = chapterTitle;
			this.content = content;
		}

	}

	@Post
	public Response postPratilipiContent( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiContentDoc pcDoc = 
				PratilipiDocUtil.updateContent( request.pratilipiId, 
												request.chapterNo, 
												request.pageNo, 
												request.chapterTitle, 
												request.content );

		return new Response( request.pratilipiId, 
								request.chapterNo, 
								request.pageNo,
								request.chapterTitle, 
								request.content );

	}

}
