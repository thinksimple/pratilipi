package com.pratilipi.common.util;

import com.google.apphosting.api.ApiProxy;

public class SystemProperty {

	public static final Long SYSTEM_USER_ID = 5636632866717696L;

	public static final String DATASOURCE;
	public static final String BLOBSERVICE_GCS_BUCKET;
	public static final String BLOBSERVICE_GCS_BUCKET_BACKUP;
	public static final String CDN;
	public static final String STAGE;
	
	static {
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		String moduleId = ApiProxy.getCurrentEnvironment().getModuleId();
		if( appId.equals( "prod-pratilipi" ) || appId.equals( "s~prod-pratilipi" ) ) {
			DATASOURCE = "gae";
			BLOBSERVICE_GCS_BUCKET = "static.pratilipi.com";
			BLOBSERVICE_GCS_BUCKET_BACKUP = "backup.pratilipi.com";
			CDN = "http://0.ptlp.co";
			STAGE = moduleId.equals( "gamma" ) || moduleId.equals( "gamma-android" ) ? "gamma" : "prod";
		} else if( appId.equals( "devo-pratilipi" ) || appId.equals( "s~devo-pratilipi" ) ) {
			DATASOURCE = "gae";
			BLOBSERVICE_GCS_BUCKET = "devo-pratilipi.appspot.com";
			BLOBSERVICE_GCS_BUCKET_BACKUP = "devo-pratilipi.appspot.com";
			CDN = "http://0.devo.ptlp.co";
			STAGE = "beta";
		} else {
			DATASOURCE = "mock";
			BLOBSERVICE_GCS_BUCKET = "localhost";
			BLOBSERVICE_GCS_BUCKET_BACKUP = "localhost";
			CDN = null;
			STAGE = "alpha";
		}
	}
	
}
