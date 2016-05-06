package com.pratilipi.data;

import com.pratilipi.common.util.SystemProperty;

public class DataAccessorFactory {

	private static final String datasource = SystemProperty.DATASOURCE;
	private static final String indexName = "GLOBAL_INDEX";
	private static final String gcsBucket = SystemProperty.BLOBSERVICE_GCS_BUCKET;
	private static final String gcsBucketBackup = SystemProperty.BLOBSERVICE_GCS_BUCKET_BACKUP;


	private static final Memcache cacheL1 = new MemcacheImpl();
	private static final Memcache cacheL2 = new MemcacheGaeImpl();
	private static final ThreadLocal<DataAccessor> threadLocalDataAccessor = new ThreadLocal<>();
	private static final SearchAccessor searchAccessor = datasource.equals( "gae" )
			? new SearchAccessorGaeImpl( indexName )
			: new SearchAccessorMockImpl( indexName );
	private static final BlobAccessor blobAccessor = new BlobAccessorGcsImpl( gcsBucket );
	private static final BlobAccessor blobAccessorBackup = new BlobAccessorGcsImpl( gcsBucketBackup );
	private static final ThreadLocal<DocAccessor> threadLocalDocAccessor = new ThreadLocal<>();

	
	public static Memcache getL1CacheAccessor() {
		return cacheL1;
	}

	public static Memcache getL2CacheAccessor() {
		return cacheL2;
	}
	
	public static DataAccessor getDataAccessor() {
		DataAccessor dataAccessor = threadLocalDataAccessor.get();
		if( dataAccessor == null ) {
			dataAccessor = datasource.equals( "gae" ) ? new DataAccessorGaeImpl() : new DataAccessorMockImpl();
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, cacheL2 );
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, new MemcacheImpl() );
			threadLocalDataAccessor.set( dataAccessor );
		}
		return dataAccessor;
	}
	
	public static void destroyDataAccessor() {
		DataAccessor dataAccessor = threadLocalDataAccessor.get();
		if( dataAccessor != null ) {
			dataAccessor.destroy();
			threadLocalDataAccessor.remove();
		}
	}
	
	public static SearchAccessor getSearchAccessor() {
		return searchAccessor;
	}

	public static BlobAccessor getBlobAccessor() {
		return new BlobAccessorWithMemcache( blobAccessor, cacheL2 );
	}

	public static BlobAccessor getBlobAccessorBackup() {
		return blobAccessorBackup;
	}

	public static DocAccessor getDocAccessor() {
		DocAccessor docAccessor = threadLocalDocAccessor.get();
		if( docAccessor == null ) {
			BlobAccessor blobAccessor = DataAccessorFactory.blobAccessor;
			blobAccessor = new BlobAccessorWithMemcache( blobAccessor, cacheL2 );
			blobAccessor = new BlobAccessorWithMemcache( blobAccessor, new MemcacheImpl() );
			docAccessor = new DocAccessorImpl( blobAccessor );
			threadLocalDocAccessor.set( docAccessor );
		}
		return docAccessor;
	}

}
