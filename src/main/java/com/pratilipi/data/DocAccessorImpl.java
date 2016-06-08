package com.pratilipi.data;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.CommentDoc;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;
import com.pratilipi.data.type.PratilipiReviewsDoc;
import com.pratilipi.data.type.UserPratilipiDoc;
import com.pratilipi.data.type.doc.CommentDocImpl;
import com.pratilipi.data.type.doc.PratilipiGoogleAnalyticsDocImpl;
import com.pratilipi.data.type.doc.PratilipiReviewsDocImpl;
import com.pratilipi.data.type.doc.UserPratilipiDocImpl;

public class DocAccessorImpl implements DocAccessor {

	private final BlobAccessor blobAccessor;
	
	
	public DocAccessorImpl( BlobAccessor blobAccessor ) {
		this.blobAccessor = blobAccessor;
	}
	
	
	private static final Logger logger =
			Logger.getLogger( DocAccessorImpl.class.getName() );

	
	// UserPratilipi Doc
	
	public UserPratilipiDoc newUserPratilipiDoc() {
		return new UserPratilipiDocImpl();
	}
	
	
	// Comment Doc
	
	public CommentDoc newCommentDoc() {
		return new CommentDocImpl();
	}
		


	// Pratilipi Reviews Doc
	
	@Override
	public PratilipiReviewsDoc newPratilipiReviewsDoc() {
		return new PratilipiReviewsDocImpl();
	}
	
	@Override
	public PratilipiReviewsDoc getPratilipiReviewsDoc( Long pratilipiId ) throws UnexpectedServerException {
		if( pratilipiId != null )
			return _get( "pratilipi/" + pratilipiId + "/reviews", PratilipiReviewsDocImpl.class );
		return null;
	}
	
	@Override
	public void save( Long pratilipiId, PratilipiReviewsDoc reviewsDoc ) throws UnexpectedServerException {
		if( pratilipiId != null )
			_save( "pratilipi/" + pratilipiId + "/reviews", reviewsDoc );
	}
	
	
	// Pratilipi GoogleAnalytics Doc
	
	@Override
	public PratilipiGoogleAnalyticsDoc getPratilipiGoogleAnalyticsDoc( Long pratilipiId ) throws UnexpectedServerException {
		if( pratilipiId != null )
			return _get( "pratilipi/" + pratilipiId + "/googleAnalytics", PratilipiGoogleAnalyticsDocImpl.class );
		return null;
	}
	
	@Override
	public void save( Long pratilipiId, PratilipiGoogleAnalyticsDoc gaDoc ) throws UnexpectedServerException {
		if( pratilipiId != null )
			_save( "pratilipi/" + pratilipiId + "/googleAnalytics", gaDoc );
	}
	
	
	private <T> T _get( String docPath, Class<T> clazz ) throws UnexpectedServerException {
		BlobEntry blobEntry = blobAccessor.getBlob( docPath );
		try {
			if( blobEntry == null )
				return clazz.newInstance();
			else
				return new Gson().fromJson(
						new String( blobEntry.getData(), "UTF-8" ),
						clazz );
		} catch( InstantiationException | IllegalAccessException | JsonSyntaxException | UnsupportedEncodingException e) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}
	}
	
	private <T> void _save( String docPath, T doc ) throws UnexpectedServerException {
		try {
			byte[] blobData = new Gson().toJson( doc ).getBytes( "UTF-8" );
			BlobEntry blobEntry = blobAccessor.newBlob( docPath, blobData, "application/json" );
			blobAccessor.createOrUpdateBlob( blobEntry );
		} catch( UnsupportedEncodingException e ) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}
	}
	
}
