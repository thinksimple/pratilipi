package com.pratilipi.api.impl.pratilipi;

import java.util.List;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.type.PratilipiContentDoc;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content/index" )
public class PratilipiContentIndexApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Long pratilipiId;

	}

	public static class Response extends GenericResponse {

		@SuppressWarnings("unused")
		private List<JsonObject> index;

		private Response() {}

		private Response( List<JsonObject> indexArray ) {
			this.index = indexArray;
		}

	}

	@Get
	public Response postAddChapter( GetRequest request )
			throws UnexpectedServerException {

		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		PratilipiContentDoc pcDoc = docAccessor.getPratilipiContentDoc( request.pratilipiId );

		return new Response( pcDoc.getIndex() );

	}

}
