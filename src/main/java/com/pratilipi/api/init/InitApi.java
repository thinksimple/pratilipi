package com.pratilipi.api.init;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.util.SystemProperty;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );
	
	
	@Get
	public GenericResponse getAuthorProcess( GenericRequest request ) {
		
		if( SystemProperty.get( "cron" ).equals( "stop" ) )
			return new GenericResponse();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query( "_ah_SESSION" )
				.setKeysOnly();
		PreparedQuery pq = datastore.prepare( query );
		FetchOptions fo = FetchOptions.Builder
				.withDefaults()
				.chunkSize( 100 )
				.limit( 1000 );

		int cleared = 0;
		try {
			for( Entity entity : pq.asIterable( fo ) ) {
				datastore.delete( entity.getKey() );
				cleared++;
			}
		} catch( Throwable e ) {
			logger.log( Level.SEVERE, "Exception occured while clearing session entites.", e );
		} finally {
			logger.log( Level.INFO, "Cleared " + cleared + " session entities." );
		}
		
		return new GenericResponse();
	}
	
}