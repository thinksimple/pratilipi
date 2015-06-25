package com.pratilipi.common.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.apphosting.api.ApiProxy;

public class SystemProperty {

	private static final Logger logger =
			Logger.getLogger( SystemProperty.class.getName() );


	public static final String get( String propertyName ) {
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		if( appId.startsWith("s~") )
			appId = appId.substring( 2 );

		String propertyValue = System.getProperty( appId + "." + propertyName );
		if( propertyValue == null || propertyValue.isEmpty() )
			logger.log( Level.WARNING, "System property '" + propertyName + "' is missing." );
		
		return propertyValue;
	}

}
