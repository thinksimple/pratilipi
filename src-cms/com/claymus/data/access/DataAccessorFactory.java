package com.claymus.data.access;

import com.claymus.commons.server.ClaymusHelper;

public class DataAccessorFactory {
	
	private static final String GOOGLE_CLOUD_STORAGE_BUCKET =
			ClaymusHelper.getSystemProperty( "blobservice.gcs.bucket" );

	private static BlobAccessor blobAccessor;
	private static Memcache memcache;
	
	public static DataAccessor getDataAccessor() {
		if( memcache == null )
			memcache = new MemcacheGaeImpl();
		return new DataAccessorWithMemcache( new DataAccessorGaeImpl(), memcache );
	}
	
	public static BlobAccessor getBlobAccessor() {
		if( blobAccessor == null ) {
			blobAccessor = new BlobAccessorGcsImpl( GOOGLE_CLOUD_STORAGE_BUCKET );
		}		
		return blobAccessor;
	}
	
}
