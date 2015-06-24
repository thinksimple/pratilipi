package com.pratilipi.common.util;

import com.google.apphosting.api.ApiProxy;

public class SystemProperty {

	public static final String get( String propertyName ) {
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		if( appId.startsWith("s~") )
			appId = appId.substring( 2 );
		return System.getProperty( appId + "." + propertyName );
	}

}
