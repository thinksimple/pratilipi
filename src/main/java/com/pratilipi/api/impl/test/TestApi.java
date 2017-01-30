package com.pratilipi.api.impl.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.RtdbAccessor;
import com.pratilipi.data.type.UserPreferenceRtdb;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );

	public static class GetRequest extends GenericRequest {

		private Integer limitTo;

	}
	@Get
	public static GenericResponse get( GetRequest request ) throws UnexpectedServerException {

		RtdbAccessor rtdbAccessor = DataAccessorFactory.getRtdbAccessor();
		List<Long> userIds = new ArrayList<>( rtdbAccessor.getUserPreferences( 25 ).keySet() );

		if( request.limitTo != null )
			userIds = userIds.subList( 0, request.limitTo );

		Long x = new Date().getTime();
		Map<Long, UserPreferenceRtdb> preferences = rtdbAccessor.getUserPreferences( userIds );
		Long y = new Date().getTime();

		logger.log( Level.INFO, "Size: " + preferences.size() );
		logger.log( Level.INFO, "Total Time : " + (y-x) + " ms." );

		return new GenericResponse();

	}

}
