package com.pratilipi.api.impl.init;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetInitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.type.Blog;
import com.pratilipi.data.type.gae.BlogEntity;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) throws InvalidArgumentException, InsufficientAccessException {

		Query<BlogEntity> query = ObjectifyService.ofy().load().type( BlogEntity.class );
		QueryResultIterator<BlogEntity> iterator = query.iterator();
		
		while( iterator.hasNext() ) {
			logger.log( Level.INFO, iterator.next().getId() + "" );
		}
		
		Blog blog = ObjectifyService.ofy().load().type( BlogEntity.class ).id( 5197509039226880L ).now();

		logger.log( Level.INFO, "Cursor: " + iterator.getCursor() );
		
		return new GenericResponse();
	}

}