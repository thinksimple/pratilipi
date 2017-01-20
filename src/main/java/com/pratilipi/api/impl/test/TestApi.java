package com.pratilipi.api.impl.test;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Long userId;

	}

	public static class Response extends GenericResponse {

		private String content;

		public Response( String content ) {
			this.content = content;
		}

	}

	@Get
	public Response get( GetRequest request ) throws UnexpectedServerException {
		return new Response( new Gson().toJson( DataAccessorFactory.getRtdbAccessor().getUserPreference( request.userId ) ) );
	}

}