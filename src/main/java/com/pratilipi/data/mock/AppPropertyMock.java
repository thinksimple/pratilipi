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
		facebookCredentials.put( "appId", "368844269953501" );
		facebookCredentials.put( "appSecret", "48db3def20329db11988e19082ffd5bb" );
		fbCredAppProperty.setValue( facebookCredentials );
		
		APP_PROPERTY_TABLE.add( fbCredAppProperty );
	}

}