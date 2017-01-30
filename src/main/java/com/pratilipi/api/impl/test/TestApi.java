package com.pratilipi.api.impl.test;

import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );

	public static class GetRequest extends GenericRequest {

		private String method;

		private Integer limitTo;

	}

	@Get
	public static GenericResponse get( GetRequest request ) throws UnexpectedServerException {

		DataAccessorFactory.getRtdbAccessor().getUserPreferences( request.method, request.limitTo );
		return new GenericResponse();

	}

}
