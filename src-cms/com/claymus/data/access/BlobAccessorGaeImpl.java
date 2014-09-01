package com.claymus.data.access;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class BlobAccessorGaeImpl implements BlobAccessor {

	@SuppressWarnings("unused")
	private static final Logger logger = 
			Logger.getLogger( BlobAccessorGaeImpl.class.getName() );

	private static final BlobstoreService blobstoreService =
			BlobstoreServiceFactory.getBlobstoreService();

	
	@Override
	public String createUploadUrl( String fileName ) {
		return blobstoreService.createUploadUrl( "/service.upload/" + fileName );
	}

	@Override
	public boolean createBlob( HttpServletRequest request, String fileName ) {
		// TODO: Persist a mapping between fileName and blob uploaded.
		// TODO: Handle multiple blob uploads.
		return false;
	}

	@Override
	public void serveBlob( String fileName, HttpServletResponse response )
			throws IOException {
		
		// TODO: Get the blob key mapped against the fileName.
		String blobKeyString = null;
		BlobKey blobKey = new BlobKey( blobKeyString );
		blobstoreService.serve( blobKey, response );
	}
	
}
