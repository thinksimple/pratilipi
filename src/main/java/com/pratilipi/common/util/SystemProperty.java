package com.pratilipi.common.util;

import com.google.apphosting.api.ApiProxy;

public class SystemProperty {

	public static final Long SYSTEM_USER_ID = 5636632866717696L;

	public static final String DATASOURCE;
	public static final String BLOBSERVICE_GCS_BUCKET;
	public static final String BLOBSERVICE_GCS_BUCKET_BACKUP;
	public static final String CDN;
	public static final String STAGE;
	
	public static final String STAGE_ALPHA	= "alpha";
	public static final String STAGE_BETA	= "beta";
	public static final String STAGE_GAMMA	= "gamma";
	public static final String STAGE_PROD	= "prod";

	static {
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		String moduleId = ApiProxy.getCurrentEnvironment().getModuleId();
		if( appId.equals( "prod-pratilipi" ) || appId.equals( "s~prod-pratilipi" ) ) {
			DATASOURCE = "gae";
			BLOBSERVICE_GCS_BUCKET = "static.pratilipi.com";
			BLOBSERVICE_GCS_BUCKET_BACKUP = "backup.pratilipi.com";
			// TODO: Update this as soon as https support is added
			CDN = moduleId.equals( "gamma" ) ? "https://d3cwrmdwk8nw1j.cloudfront.net" : "http://*.ptlp.co";
			STAGE = moduleId.equals( "gamma" ) || moduleId.equals( "gamma-android" ) ? STAGE_GAMMA : STAGE_PROD;
		} else if( appId.equals( "devo-pratilipi" ) || appId.equals( "s~devo-pratilipi" ) ) {
			DATASOURCE = "gae";
			BLOBSERVICE_GCS_BUCKET = "devo-pratilipi.appspot.com";
			BLOBSERVICE_GCS_BUCKET_BACKUP = "devo-pratilipi.appspot.com";
			CDN = "http://*.devo.ptlp.co";
			STAGE = STAGE_BETA;
		} else {
			DATASOURCE = "mock";
			BLOBSERVICE_GCS_BUCKET = "localhost";
			BLOBSERVICE_GCS_BUCKET_BACKUP = "localhost";
			CDN = null;
			STAGE = STAGE_ALPHA;
		}
	}
	
}
