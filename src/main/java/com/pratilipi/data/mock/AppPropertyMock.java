package com.pratilipi.data.mock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.gae.AppPropertyEntity;

public class AppPropertyMock {
	
	public static final List<AppProperty> APP_PROPERTY_TABLE = new LinkedList<>();
	
	public static final AppPropertyEntity fbCredAppProperty = new AppPropertyEntity( AppProperty.FACEBOOK_CREDENTIALS );

	
	static {
		Map<String, String> facebookCredentials = new HashMap<String, String>();
		facebookCredentials.put( "appId", "123456789012345" );
		facebookCredentials.put( "appSecret", "12345678765432123456765432" );
		fbCredAppProperty.setValue( facebookCredentials );
		
		APP_PROPERTY_TABLE.add( fbCredAppProperty );
	}

}

