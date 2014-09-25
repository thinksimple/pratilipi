package com.claymus.data.access;

import com.claymus.commons.server.ClaymusHelper;

public class DataAccessorFactory {
	
	private static final String GOOGLE_CLOUD_STORAGE_BUCKET =
			ClaymusHelper.getSystemProperty( "blobservice.gcs.bucket" );

	private static final Memcache cacheL1 = new MemcacheClaymusImpl();
	private static final Memcache cacheL2 = new MemcacheGaeImpl();
	private static BlobAccessor blobAccessor;
	

	public static Memcache getL1CacheAccessor() {
		return cacheL1;
	}

	public static Memcache getL2CacheAccessor() {
		return cacheL2;
	}
	
	public static DataAccessor getDataAccessor() {
		return new DataAccessorWithMemcache( new DataAccessorGaeImpl(), cacheL2 );
	}
	
	public static BlobAccessor getBlobAccessor() {
		if( blobAccessor == null )
			blobAccessor = new BlobAccessorWithMemcache(
					new BlobAccessorGcsImpl( GOOGLE_CLOUD_STORAGE_BUCKET ),
					cacheL2 );
		return blobAccessor;
	}
	
}
