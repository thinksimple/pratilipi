package com.pratilipi.api.impl.pratilipi;

import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.pratilipi.common.util.PratilipiContentUtil;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.type.PratilipiContentDoc;
import com.pratilipi.data.type.PratilipiContentDoc.Chapter;

@SuppressWarnings({ "unused", "serial" })
@Bind( uri = "/pratilipi/content/chapter" )
public class PratilipiContentChapterApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( PratilipiContentChapterApi.class.getName() );

	public static class GetRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		@Validate( required = true, minInt = 1, requiredErrMsg = ERR_PRATILIPI_CHAPTER_NO_REQUIRED )
		private Integer chapterNo;

		private boolean asHtml;

	}

	public static class PostRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		@Validate( required = true, minInt = 1, requiredErrMsg = ERR_PRATILIPI_CHAPTER_NO_REQUIRED )
		private Integer chapterNo;

		private String chapterTitle;

		private String content;

	}

	public static class Response extends GenericResponse {

		private Long pratilipiId;
		private Integer chapterNo;
		private String chapterTitle;
		private Object content;

		private Response() {}

		private Response( Long pratilipiId, Integer chapterNo, String chapterTitle, Object content ) {
			this.pratilipiId = pratilipiId;
			this.chapterNo = chapterNo;
			this.chapterTitle = chapterTitle;
			this.content = content;
		}

	}

	@Get
	public Response getPratilipiContent( GetRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		PratilipiContentDoc pcDoc = docAccessor.getPratilipiContentDoc( request.pratilipiId );

		if( pcDoc == null )
			return new Response();

		Chapter chapter = pcDoc.getChapter( request.chapterNo );
		return new Response( request.pratilipiId, request.chapterNo, chapter.getTitle(), chapter.getContent( request.asHtml ) );

	}

	@Post
	public Response postPratilipiContent( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiContentUtil.updateChapter( request.pratilipiId, request.chapterNo, request.chapterTitle, request.content );
		return new Response( request.pratilipiId, request.chapterNo, request.chapterTitle, request.content );

	}

}
