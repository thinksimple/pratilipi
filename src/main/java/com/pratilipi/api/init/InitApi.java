package com.pratilipi.api.init;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.init.shared.GetInitRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );
	
	@Get
	public GenericResponse get( GetInitRequest request ) throws IOException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		AppProperty sharedProperty = dataAccessor.getAppProperty( "Test.Key.Shared" );
		if( sharedProperty == null ) {
			JsonObject sharedObject = new JsonObject();
			sharedObject.addProperty( "Test.Key.1", 0 );
			sharedObject.addProperty( "Test.Key.2", 0 );
			sharedProperty = dataAccessor.newAppProperty( "Test.Key.Shared" );
			sharedProperty.setValue( sharedObject );
			dataAccessor.createOrUpdateAppProperty( sharedProperty );
		}

		AppProperty property = dataAccessor.getAppProperty( request.getPropertyName() );
		if( property == null ) {
			property = dataAccessor.newAppProperty( request.getPropertyName() );
			property.setValue( 0 );
			dataAccessor.createOrUpdateAppProperty( property );
		}
		
		
		JsonObject sharedObject = sharedProperty.getValue();
		int value = sharedObject.get( request.getPropertyName() ).getAsInt();
		int expectedValue = property.getValue();
		if( value != expectedValue )
			logger.log( Level.SEVERE, "Found: " + value + ", Expected: " + expectedValue );
		else
			logger.log( Level.INFO, "Found: " + value + ", Expected: " + expectedValue );

		
		int newValue = expectedValue + 1;

		sharedObject.addProperty( request.getPropertyName(), newValue );
		sharedProperty.setValue( sharedObject );
		logger.log( Level.INFO, sharedObject.toString() );
		dataAccessor.createOrUpdateAppProperty( sharedProperty );

		property.setValue( newValue );
		dataAccessor.createOrUpdateAppProperty( property );
		
		return new GenericResponse();
	}
	
}