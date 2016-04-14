package com.pratilipi.api.impl.init;

import java.util.List;
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
import com.pratilipi.data.type.gae.BlogPostEntity;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) throws InvalidArgumentException, InsufficientAccessException {

		Query<BlogPostEntity> query = ObjectifyService.ofy().load().type( BlogPostEntity.class ).limit( 20 );
		QueryResultIterator<BlogPostEntity> iterator = query.iterator();
		
//		while( iterator.hasNext() )
//			logger.log( Level.INFO, iterator.next().getId() + "" );

		List<BlogPostEntity> list = query.list();
		for( BlogPostEntity post : list )
			logger.log( Level.INFO, post.getId() + "" );
		
		logger.log( Level.INFO, "Cursor: " + iterator.getCursor() );
		
		return new GenericResponse();
	}

}