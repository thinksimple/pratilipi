package com.pratilipi.api.impl.test;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailState;
import com.pratilipi.data.type.gae.EmailEntity;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );
	
	public static class Request extends GenericRequest {

		private Integer limit;

		public void setLimit( Integer limit ) {
			this.limit = limit;
		}

	}

	@Get
	public static GenericResponse get( Request request ) throws UnexpectedServerException {

		if( request.limit == null )
			request.setLimit( 2000 );

		Long x = new Date().getTime();
		// Finding out count
		int count = ObjectifyService.ofy().load()
						.type( EmailEntity.class )
						.filter( "STATE", EmailState.SENT )
						.limit( request.limit )
						.count();
		Long y = new Date().getTime();

		logger.log( Level.INFO, "count = " + count + " ms." );
		logger.log( Level.INFO, "Time taken for finding count = " + ( y-x ) + " ms." );

		return new GenericResponse();

	}

}
