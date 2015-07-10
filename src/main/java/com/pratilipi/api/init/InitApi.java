package com.pratilipi.api.init;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );
	
	
	@Get
	public GenericResponse getAuthorProcess( GenericRequest request ) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		
		// Cleaning up _ah_SESSION table
		
		Query query = new Query( "_ah_SESSION" ).setKeysOnly();
		PreparedQuery pq = datastore.prepare( query );
		FetchOptions fo = FetchOptions.Builder.withDefaults()
				.chunkSize( 100 ).limit( 1000 );

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
		

		// Cleaning up ACCESS_TOKEN table

		Filter creationDateFilter = new FilterPredicate(
				"CREATION_DATE",
				FilterOperator.LESS_THAN,
				new Date( new Date().getTime() - 30 * 24 * 60 * 60 * 1000 ) );
		
		Filter publisherIdFilter = new FilterPredicate(
				"PUBLISHER_ID",
				FilterOperator.EQUAL,
				null );

		Filter userIdFilter = new FilterPredicate(
				"USER_ID",
				FilterOperator.EQUAL,
				0L );

		query = new Query( "ACCESS_TOKEN" )
				.setFilter( CompositeFilterOperator.and( creationDateFilter, publisherIdFilter, userIdFilter ) )
				.setKeysOnly();
		pq = datastore.prepare( query );
		fo = FetchOptions.Builder.withDefaults()
				.chunkSize( 100 ).limit( 1000 );
		
		cleared = 0;
		try {
			for( Entity entity : pq.asIterable( fo ) ) {
				logger.log( Level.INFO, entity.toString() );
//				datastore.delete( entity.getKey() );
				cleared++;
			}
		} catch( Throwable e ) {
			logger.log( Level.SEVERE, "Exception occured while clearing access token entites.", e );
		} finally {
			logger.log( Level.INFO, "Cleared " + cleared + " access token entities." );
		}

		
		return new GenericResponse();
	}
	
}