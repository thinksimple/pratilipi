package com.pratilipi.data;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.CommentDoc;
import com.pratilipi.data.type.PratilipiContentDoc;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;
import com.pratilipi.data.type.PratilipiReviewsDoc;
import com.pratilipi.data.type.UserPratilipiDoc;

public interface DocAccessor {

	// UserPratilipi Doc
	UserPratilipiDoc newUserPratilipiDoc();
	
	// Comment Doc
	CommentDoc newCommentDoc();
	


	// Pratilipi Content Doc
	PratilipiContentDoc getPratilipiContentDoc();
	
	// Pratilipi Reviews Doc
	PratilipiReviewsDoc newPratilipiReviewsDoc();
	PratilipiReviewsDoc getPratilipiReviewsDoc( Long pratilipiId ) throws UnexpectedServerException;
	void save( Long pratilipiId, PratilipiReviewsDoc reviewsDoc ) throws UnexpectedServerException;
	

	// Pratilipi GoogleAnalytics Doc
	PratilipiGoogleAnalyticsDoc getPratilipiGoogleAnalyticsDoc( Long pratilipiId ) throws UnexpectedServerException;
	void save( Long pratilipiId, PratilipiGoogleAnalyticsDoc gaDoc ) throws UnexpectedServerException;
	
}
