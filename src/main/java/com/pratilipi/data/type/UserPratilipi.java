package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.UserReviewState;

public interface UserPratilipi extends Serializable {

	String getId();
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );

	
	Integer getLastOpenedPage();
	
	void setLastOpenedPage( Integer lastOpenedPage );

	Date getLastOpenedDate();
	
	void setLastOpenedDate( Date lastOpenedDate );

	
	Integer getRating();
	
	void setRating( Integer rating );
	
	String getReview();
	
	void setReview( String review );
	
	UserReviewState getReviewState();
	
	void setReviewState( UserReviewState reviewState );

	Date getReviewDate();
	
	void setReviewDate( Date reviewDate );

	
	Boolean isAddedtoLib();
	
	void setAddedToLib( Boolean addedToLib );
	
}
