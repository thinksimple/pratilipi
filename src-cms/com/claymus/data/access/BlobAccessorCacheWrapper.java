package com.claymus.data.access;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.transfer.BlobEntry;

public class BlobAccessorCacheWrapper implements BlobAccessor {

	private static final Logger logger = 
			Logger.getLogger( BlobAccessorCacheWrapper.class.getName() );

	
	private final int maxCacheSize = 100 * 1024 * 1024; // 100 MB
	
	@SuppressWarnings("serial")
	private final Map<String, BlobEntry> blobEntryCache
			= new LinkedHashMap<String, BlobEntry>( 128, 0.75f, true ) {

		@Override
		protected boolean removeEldestEntry( Map.Entry<String, BlobEntry> eldest ) {
			int cacheSize = 0;
			for( BlobEntry blobEntry : blobEntryCache.values() ) {
				cacheSize = cacheSize + blobEntry.getData().length;
				if( cacheSize > maxCacheSize )
					return true;
			}
			return false;
		}

	};
	
	private final BlobAccessor blobAccessor;
	
	
	public BlobAccessorCacheWrapper( BlobAccessor blobAccessor ) {
		this.blobAccessor = blobAccessor;
	}
	
	
	@Override
	public String createUploadUrl( String fileName ) {
		return blobAccessor.createUploadUrl( fileName );
	}

	@Override
	public boolean createBlob( HttpServletRequest request, String fileName ) {
		boolean blobCreated = blobAccessor.createBlob( request, fileName );
		if( blobCreated )
			blobEntryCache.remove( fileName );
		return blobCreated;
	}

	@Override
	public BlobEntry getBlob( String fileName ) throws IOException {
		BlobEntry blobEntry = blobEntryCache.get( fileName );
		if( blobEntry == null ) {
			logger.log( Level.INFO, "Cache Miss ! Object Name: " + fileName );
			blobEntry = blobAccessor.getBlob( fileName );
			blobEntryCache.put( fileName, blobEntry );
		} else {
			logger.log( Level.INFO, "Cache Hit ! Object Name: " + fileName );
		}
		return blobEntry;
	}
	
	@Override
	public void serveBlob( String fileName, HttpServletResponse response )
			throws IOException {
		blobAccessor.serveBlob( fileName, response );
	}
	
}
