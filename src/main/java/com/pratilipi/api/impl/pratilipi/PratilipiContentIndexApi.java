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

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		public void setPratilipiId( Long pratilipiId )  {
			this.pratilipiId = pratilipiId;
		}

	}

	public static class Response extends GenericResponse {
		
		private List<JsonObject> index;

		private Response() {}

		public Response( List<JsonObject> indexArray ) {
			this.index = indexArray;
		}

		public List<JsonObject> getIndex() {
			return index;
		}

	}

	@Get
	public Response getIndex( GetRequest request )
			throws UnexpectedServerException {

		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		PratilipiContentDoc pcDoc = docAccessor.getPratilipiContentDoc( request.pratilipiId );

		if( pcDoc != null )
			return new Response( pcDoc.getIndex() );

		return new Response();

	}

}
