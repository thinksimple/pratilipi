package com.pratilipi.common.util;

import com.google.apphosting.api.ApiProxy;

public class SystemProperty {

	public static final String DATASOURCE;
	public static final String BLOBSERVICE_GCS_BUCKET;
	public static final String BLOBSERVICE_GCS_BUCKET_BACKUP;
	public static final String CDN;

	static {
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		if( appId.equals( "prod-pratilipi" ) || appId.equals( "s~prod-pratilipi" ) ) {
			DATASOURCE = "gae";
			BLOBSERVICE_GCS_BUCKET = "static.pratilipi.com";
			BLOBSERVICE_GCS_BUCKET_BACKUP = "backup.pratilipi.com";
			CDN = "http://*.ptlp.co";
		} else if( appId.equals( "devo-pratilipi" ) || appId.equals( "s~devo-pratilipi" ) ) {
			DATASOURCE = "gae";
			BLOBSERVICE_GCS_BUCKET = "devo-pratilipi.appspot.com";
			BLOBSERVICE_GCS_BUCKET_BACKUP = "devo-pratilipi.appspot.com";
			CDN = "http://*.devo.ptlp.co";
		} else {
			DATASOURCE = "mock";
			BLOBSERVICE_GCS_BUCKET = "localhost";
			BLOBSERVICE_GCS_BUCKET_BACKUP = "localhost";
			CDN = null;
		}
	}
	
}
