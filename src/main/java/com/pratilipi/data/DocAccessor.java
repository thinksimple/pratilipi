package com.pratilipi.data;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.CommentDoc;
import com.pratilipi.data.type.InitBannerDoc;
import com.pratilipi.data.type.InitDoc;
import com.pratilipi.data.type.PratilipiContentDoc;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;
import com.pratilipi.data.type.PratilipiReviewsDoc;
import com.pratilipi.data.type.UserPratilipiDoc;

public interface DocAccessor {

	// UserPratilipi Doc
	UserPratilipiDoc newUserPratilipiDoc();
	
	// Comment Doc
	CommentDoc newCommentDoc();

	
	InitDoc newInitDoc();
	
	InitBannerDoc newInitBannerDoc( String bannerId, String title, String actionUrl );


	// Pratilipi Content Doc
	PratilipiContentDoc newPratilipiContentDoc();
	PratilipiContentDoc getPratilipiContentDoc( Long pratilipiId ) throws UnexpectedServerException;
	void save( Long pratilipiId, PratilipiContentDoc contentDoc ) throws UnexpectedServerException;
	
	// Pratilipi Reviews Doc
	PratilipiReviewsDoc newPratilipiReviewsDoc();
	PratilipiReviewsDoc getPratilipiReviewsDoc( Long pratilipiId ) throws UnexpectedServerException;
	void save( Long pratilipiId, PratilipiReviewsDoc reviewsDoc ) throws UnexpectedServerException;
	

	// Pratilipi GoogleAnalytics Doc
	PratilipiGoogleAnalyticsDoc getPratilipiGoogleAnalyticsDoc( Long pratilipiId ) throws UnexpectedServerException;
	void save( Long pratilipiId, PratilipiGoogleAnalyticsDoc gaDoc ) throws UnexpectedServerException;
	
}
