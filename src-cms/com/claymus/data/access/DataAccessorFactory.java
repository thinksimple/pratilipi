package com.claymus.data.access;

import com.claymus.ClaymusHelper;

public class DataAccessorFactory {
	
	private static final String GOOGLE_CLOUD_STORAGE =
			"GOOGLE_CLOUD_STORAGE";
	private static final String GOOGLE_APPENGINE_BLOBSTORE =
			"GOOGLE_APPENGINE_BLOBSTORE";

	
	private static final String CLOUD_SERVICE =
			ClaymusHelper.getSystemProperty( "blobservice" );

	private static final String GOOGLE_CLOUD_STORAGE_BUCKET =
			ClaymusHelper.getSystemProperty( "blobservice.gcs.bucket" );

	
	public static DataAccessor getDataAccessor() {
		return new DataAccessorGaeImpl();
	}
	
	public static BlobAccessor getBlobAccessor() {

		if( CLOUD_SERVICE.equals( GOOGLE_CLOUD_STORAGE ) )
			return new BlobAccessorGcsImpl( GOOGLE_CLOUD_STORAGE_BUCKET );
		
		else if( CLOUD_SERVICE.equals( GOOGLE_APPENGINE_BLOBSTORE ) )
			return new BlobAccessorGaeImpl();
		
		return null;
	}
	
}
