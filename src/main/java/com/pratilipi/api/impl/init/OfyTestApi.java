package com.pratilipi.api.impl.init;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetInitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.data.type.gae.BlogPostEntity;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) throws InvalidArgumentException, InsufficientAccessException {

		Query<BlogPostEntity> query
				= ObjectifyService.ofy().load().type( BlogPostEntity.class );
		
		query = query.filter( "BLOG_ID", 5197509039226880L );
		query = query.filter( "STATE != ", BlogPostState.DELETED );
		query = query.order( "STATE" );
		query = query.order( "-CREATION_DATE" );
		query = query.limit( 10 );
		
		logger.log( Level.INFO, "cusor: " + query.iterator().getCursor() );
		logger.log( Level.INFO, query.list() + " results found." );
		
		return new GenericResponse();
	}

}