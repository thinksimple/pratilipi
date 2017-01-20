package com.pratilipi.api.impl.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.RtdbAccessor;
import com.pratilipi.data.type.UserPreferenceRtdb;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {
	
	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );

	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Long userId;

	}

	@Get
	public GenericResponse get( GetRequest request ) throws UnexpectedServerException {

		RtdbAccessor rtDbAccessor = DataAccessorFactory.getRtdbAccessor();
		UserPreferenceRtdb preference = rtDbAccessor.getUserPreference( request.userId );

		logger.log( Level.INFO, "UserId: " + request.userId );
		logger.log( Level.INFO, "EmailFrequency: " + preference.getEmailFrequency() );
		logger.log( Level.INFO, "EmailFrequency: " + preference.isNotificationSubscribed( NotificationType.AUTHOR_FOLLOW ) );

		return new GenericResponse();

	}

}