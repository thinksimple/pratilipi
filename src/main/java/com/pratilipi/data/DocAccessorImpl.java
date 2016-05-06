package com.pratilipi.data;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;
import com.pratilipi.data.type.doc.PratilipiGoogleAnalyticsDocImpl;

public class DocAccessorImpl implements DocAccessor {

	private final BlobAccessor blobAccessor;
	
	
	public DocAccessorImpl( BlobAccessor blobAccessor ) {
		this.blobAccessor = blobAccessor;
	}
	
	
	private static final Logger logger =
			Logger.getLogger( DocAccessorImpl.class.getName() );
	
	
	public PratilipiGoogleAnalyticsDoc getPratilipiGoogleAnalyticsDoc( Long pratilipiId )
			throws UnexpectedServerException {
		
		if( pratilipiId == null )
			return null;
		
		Gson gson = new Gson();
		
		String fileName = "pratilipi/" + pratilipiId + "/googleAnalytics";
		BlobEntry blobEntry = blobAccessor.getBlob( fileName );
		
		if( blobEntry == null )
			return new PratilipiGoogleAnalyticsDocImpl();
		
		try {
			return gson.fromJson(
					new String( blobEntry.getData(), "UTF-8" ),
					PratilipiGoogleAnalyticsDocImpl.class );
		} catch( JsonSyntaxException | UnsupportedEncodingException e) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}
		
	}

	public void save( Long pratilipiId, PratilipiGoogleAnalyticsDoc gaDoc )
			throws UnexpectedServerException {
		
		if( pratilipiId == null )
			return;
		
		Gson gson = new Gson();
		
		String fileName = "pratilipi/" + pratilipiId + "/googleAnalytics";
		BlobEntry blobEntry = blobAccessor.getBlob( fileName );
		
		try {
			byte[] blobData = gson.toJson( gaDoc ).getBytes( "UTF-8" );
			if( blobEntry == null )
				blobEntry = blobAccessor.newBlob( fileName, blobData, "application/json" );
			else
				blobEntry.setData( blobData );
		} catch( UnsupportedEncodingException e ) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}
		
		blobAccessor.createOrUpdateBlob( blobEntry );
		
	}
	
}
