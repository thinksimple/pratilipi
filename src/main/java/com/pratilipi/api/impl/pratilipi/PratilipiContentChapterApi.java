package com.pratilipi.api.impl.pratilipi;

import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.util.PratilipiDocUtil;

@SuppressWarnings({ "unused", "serial" })
@Bind( uri = "/pratilipi/content/chapter" )
public class PratilipiContentChapterApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( PratilipiContentChapterApi.class.getName() );

	public static class GetRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		@Validate( required = true, minInt = 1, requiredErrMsg = ERR_PRATILIPI_CHAPTER_NO_REQUIRED )
		private Integer chapterNo;

		@Validate( required = true, minInt = 1, requiredErrMsg = ERR_PRATILIPI_PAGE_NO_REQUIRED )
		private Integer pageNo;

		private boolean asHtml;

	}

	public static class PostRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		@Validate( required = true, minInt = 1, requiredErrMsg = ERR_PRATILIPI_CHAPTER_NO_REQUIRED )
		private Integer chapterNo;

		@Validate( required = true, minInt = 1, requiredErrMsg = ERR_PRATILIPI_PAGE_NO_REQUIRED )
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

	@Get
	public Response getPratilipiContent( GetRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		JsonObject jsonObject = PratilipiDocUtil.getContent( request.pratilipiId, request.chapterNo, request.pageNo, request.asHtml );

		if( jsonObject == null )
			return new Response();

		return new Response( request.pratilipiId, 
								request.chapterNo, 
								request.pageNo,
								jsonObject.get( "chapterTitle" ).getAsString(), 
								jsonObject.get( "content" ).getAsString() );

	}

	@Post
	public Response postPratilipiContent( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiDocUtil.updateContent( request.pratilipiId, request.chapterNo, request.pageNo, request.chapterTitle, request.content );
		return new Response( request.pratilipiId, 
								request.chapterNo, 
								request.pageNo,
								request.chapterTitle, 
								request.content );

	}

}
