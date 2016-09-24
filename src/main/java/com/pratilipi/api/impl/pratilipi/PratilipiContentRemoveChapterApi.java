package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.type.PratilipiContentDoc;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content/chapter/remove" )
public class PratilipiContentRemoveChapterApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		@Validate( required = true )
		private Long pratilipiId;

		@Validate( required = true )
		private int chapterNo;

	}

	@Post
	public GenericResponse postAddChapter( PostRequest request ) 
			throws UnexpectedServerException, InvalidArgumentException {

		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		PratilipiContentDoc pcDoc = docAccessor.getPratilipiContentDoc( request.pratilipiId );

		if( pcDoc == null )
			throw new InvalidArgumentException( "Content is Missing!" );

		pcDoc.removeChapter( request.chapterNo );
		docAccessor.save( request.pratilipiId, pcDoc );
		return new GenericResponse();

	}

}
