package com.pratilipi.api.impl.pratilipi;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
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

		private List<JsonObject> index;

		@SuppressWarnings("unused")
		private Response() {}

		public Response( List<JsonObject> index ) {
			this.index = index;
		}


		public List<JsonObject> getIndex() {
			return index;
		}

	}

	@Get
	public Response getIndex( GetRequest request )
			throws UnexpectedServerException, InsufficientAccessException, InvalidArgumentException {

		return new Response( PratilipiDocUtil.getIndex( request.pratilipiId ) );

	}

}
