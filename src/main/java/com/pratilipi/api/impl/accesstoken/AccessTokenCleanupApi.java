package com.pratilipi.api.impl.accesstoken;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AuditLogEntity;

@SuppressWarnings("serial")
@Bind( uri = "/accesstoken/cleanup" )
public class AccessTokenCleanupApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( AccessTokenCleanupApi.class.getName() );
	
	
	@Get
	public GenericResponse get( GenericRequest request ) throws InsufficientAccessException, InvalidArgumentException, UnexpectedServerException, IOException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Fetching AppProperty
		AppProperty appProperty = dataAccessor.getAppProperty( AppProperty.API_ACCESSTOKEN_CLEANUP );
		if( appProperty == null )
			appProperty = dataAccessor.newAppProperty( AppProperty.API_NOTIFICATION_PROCESS );
		
		
		Query<AccessTokenEntity> query = ObjectifyService.ofy().load()
				.type( AccessTokenEntity.class )
				.order( "EXPIRY" )
				.limit( 1000 );

		if( appProperty.getValue() != null )
			query.startAt( Cursor.fromWebSafeString( (String) appProperty.getValue() ) );
		
		QueryResultIterator<AccessTokenEntity> iterator = query.iterator();
		while( iterator.hasNext() ) {
			AccessToken accessToken = iterator.next();
			if( ! accessToken.isExpired() )
				continue;
			AuditLog auditLog = ObjectifyService.ofy().load()
					.type( AuditLogEntity.class )
					.filter( "ACCESS_ID", accessToken.getId() )
					.first().now();
			if( auditLog != null )
				continue;
			logger.log( Level.INFO, "Deleting " + accessToken.getId() );
			ObjectifyService.ofy().delete().entity( accessToken );
		}
		
		
		// Updating AppProperty
		appProperty.setValue( iterator.getCursor().toWebSafeString() );
		dataAccessor.createOrUpdateAppProperty( appProperty );
		
		
		return new GenericResponse();
		
	}
	
}
