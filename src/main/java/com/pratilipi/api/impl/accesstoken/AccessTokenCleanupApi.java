package com.pratilipi.api.impl.accesstoken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.pratilipi.data.type.gae.AccessTokenEntity;

@SuppressWarnings("serial")
@Bind( uri = "/accesstoken/cleanup" )
public class AccessTokenCleanupApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( AccessTokenCleanupApi.class.getName() );
	
	
	@Get
	public GenericResponse get( GenericRequest request ) {

		int batchSize = 1000;

		QueryResultIterator<Key<AccessTokenEntity>> itr = ObjectifyService.ofy().load()
				.type( AccessTokenEntity.class )
				.filter( "EXPIRY <", new Date() )
				.chunk( batchSize )
				.keys()
				.iterator();

		List<Key<AccessTokenEntity>> list = new ArrayList<>( batchSize );
		while( true ) {
			if( ! itr.hasNext() || list.size() == batchSize ) {
				logger.log( Level.INFO, "Deleting " + list.size() + " AccessToken entities ..." );
				ObjectifyService.ofy().delete().keys( list );
				if( ! itr.hasNext() )
					break;
				list.clear();
			} else {
				list.add( itr.next() );
			}
		}
		
		return new GenericResponse();
		
	}
	
}
