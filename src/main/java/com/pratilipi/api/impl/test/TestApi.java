package com.pratilipi.api.impl.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.gae.PageEntity;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );

	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {

		QueryResultIterator<Key<PageEntity>> it = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.filterKey( ">=", 4503702338535424L )
				.filterKey( "<=", 4503741840490496L )
				.keys()
				.iterator();

		while( it.hasNext() )
			logger.log( Level.INFO, it.next().getId() + "" );

		return new GenericResponse();

	}

}
