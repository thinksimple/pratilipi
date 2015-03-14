package com.pratilipi.data.access;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.access.BlobAccessorGcsImpl;
import com.claymus.data.access.BlobAccessorWithMemcache;
import com.claymus.data.access.MemcacheClaymusImpl;

public class DataAccessorFactory
		extends com.claymus.data.access.DataAccessorFactory {

	protected static final String GOOGLE_CLOUD_STORAGE_BUCKET_ASIA =
			ClaymusHelper.getSystemProperty( "blobservice.gcs.bucket.asia" );

	private static final SearchAccessor searchAccessor = new SearchAccessorGaeImpl( GOOGLE_APP_ENGINE_SEARCH_INDEX );
	private static final BlobAccessor blobAccessorAsia = new BlobAccessorGcsImpl( GOOGLE_CLOUD_STORAGE_BUCKET_ASIA );

	
	public static DataAccessor getDataAccessor( HttpServletRequest request ) {
		DataAccessor dataAccessor = cacheL1.get( "PratilipiDataAccessor-" + request.hashCode() );
		if( dataAccessor == null ) {
			dataAccessor = new DataAccessorGaeImpl();
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, cacheL2 );
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, new MemcacheClaymusImpl() );
			cacheL1.put( "DataAccessor-" + request.hashCode(), dataAccessor );
			cacheL1.put( "PratilipiDataAccessor-" + request.hashCode(), dataAccessor );
		}
		return dataAccessor;
	}
	
	public static SearchAccessor getSearchAccessor() {
		return searchAccessor;
	}
	
	public static BlobAccessor getBlobAccessorAsia() {
		return new BlobAccessorWithMemcache( blobAccessorAsia, cacheL2 );
	}

}
