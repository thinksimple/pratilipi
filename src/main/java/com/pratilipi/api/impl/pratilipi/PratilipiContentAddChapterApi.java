package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.type.PratilipiContentDoc;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content/chapter/add" )
public class PratilipiContentAddChapterApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		@Validate( required = true )
		private Long pratilipiId;

		private Integer offset;

	}

	@Post
	public GenericResponse postAddChapter( PostRequest request )
			throws UnexpectedServerException {

		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		PratilipiContentDoc pcDoc = docAccessor.getPratilipiContentDoc( request.pratilipiId );

		if( pcDoc == null )
			pcDoc = docAccessor.newPratilipiContentDoc();

		pcDoc.addChapter( null, request.offset );
		docAccessor.save( request.pratilipiId, pcDoc );

		return new GenericResponse();

	}

}
