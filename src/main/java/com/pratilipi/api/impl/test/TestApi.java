package com.pratilipi.api.impl.test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.UserPratilipi;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );
	
	public static class Request extends GenericRequest {

		@Validate( required = true )
		private Long pratilipiId;
		
		public void setPratilipiId( Long pratilipiId ) {
			this.pratilipiId = pratilipiId;
		}

	}

	@Get
	public static GenericResponse get( Request request ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		List<UserPratilipi> userPratilipiList = dataAccessor.getUserPratilipiList( null, request.pratilipiId, null, null, true ).getDataList();

		logger.log( Level.INFO, "Found " + userPratilipiList.size() + " reviews for the pratilipiId." );
		logger.log( Level.INFO, new Gson().toJson( userPratilipiList ) );

		return new GenericResponse();

	}

}
