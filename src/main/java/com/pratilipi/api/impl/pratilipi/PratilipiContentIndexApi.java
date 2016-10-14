package com.pratilipi.api.impl.pratilipi;

import com.google.gson.JsonArray;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.util.PratilipiDocUtil;


@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content/index" )
public class PratilipiContentIndexApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED, minLong = 1L )
		private Long pratilipiId;


		public void setPratilipiId( Long pratilipiId )  {
			this.pratilipiId = pratilipiId;
		}

	}

	public static class Response extends GenericResponse {

		private JsonArray index;

		
		@SuppressWarnings("unused")
		private Response() {}

		public Response( JsonArray index ) {
			this.index = index;
		}


		public JsonArray getIndex() {
			return index;
		}

	}

	
	@Get
	public Response getIndex( GetRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		return new Response( PratilipiDocUtil.getContentIndex( request.pratilipiId ) );

	}

}
