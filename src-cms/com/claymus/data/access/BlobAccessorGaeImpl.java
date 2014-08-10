package com.claymus.data.access;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class BlobAccessorGaeImpl implements BlobAccessor {

	private static final Logger logger = 
			Logger.getLogger( BlobAccessorGaeImpl.class.getName() );

	private static final BlobstoreService blobstoreService =
			BlobstoreServiceFactory.getBlobstoreService();

	
	@Override
	public String createUploadUrl( String fileName ) {
		return blobstoreService.createUploadUrl( "/service.upload/" + fileName );
	}

	@Override
	public boolean createBlob( HttpServletRequest request ) {
		@SuppressWarnings("unused")
		String fileName = request.getRequestURI().substring( 16 );
		// TODO: Persist a mapping between fileName and blob uploaded.
		// TODO: Handle multiple blob uploads.
		return false;
	}

	@Override
	public boolean serveBlob( HttpServletRequest request, HttpServletResponse response ) {
		@SuppressWarnings("unused")
		String fileName = request.getRequestURI().substring( 16 );
		// TODO: Get the blob key mapped against the fileName.
		String blobKeyString = null;
		BlobKey blobKey = new BlobKey( blobKeyString );
		try {
			blobstoreService.serve( blobKey, response );
			return true;
		} catch (IOException e) {
			logger.log( Level.SEVERE, "Failed to serve the blob.", e );
			return false;
		}
	}
	
}
