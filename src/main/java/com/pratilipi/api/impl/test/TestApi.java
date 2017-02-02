package com.pratilipi.api.impl.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListIterator;
import com.pratilipi.data.type.Email;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );
	
	public static class Request extends GenericRequest {

		@Validate( required = true )
		private Long userId;
		
		public void setUserId( Long userId ) {
			this.userId = userId;
		}

	}

	@Get
	public static GenericResponse get( Request request ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		DataListIterator<Email> it = dataAccessor.getEmailListIteratorForStatePending( request.userId, true );
		List<Email> emailList = new ArrayList<>();
		while( it.hasNext() )
			emailList.add( it.next() );

		logger.log( Level.INFO, "Found " + emailList.size() + " emails for the user." );

		return new GenericResponse();

	}

}
