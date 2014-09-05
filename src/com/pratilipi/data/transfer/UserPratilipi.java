package com.pratilipi.data.transfer;

import java.util.Date;

import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.commons.shared.UserReviewState;

public interface UserPratilipi {

	String getId();
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );
	
	PratilipiType getPratilipiType();
	
	void setPratilipiType( PratilipiType pratilipiType );
	
	Long getRating();
	
	void setRating( Long rating );
	
	String getReview();
	
	void setReview( String review );
	
	UserReviewState getReviewState();
	
	void setReviewState( UserReviewState reviewState );
	
	Date getReviewDate();
	
	void setReviewDate( Date reviewDate );

}
